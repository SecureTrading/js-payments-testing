package util;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.client.WireMock;

public class MocksHandler {

    public static void stubVersion1() {
        WireMock.configureFor("localhost", 8760);
        WireMock.stubFor(get(urlEqualTo("/pet"))
                .willReturn(aResponse().withStatus(200).withBodyFile("response1.json")));
    }

    public static void stubVersion2() {
        WireMock.configureFor("localhost", 8760);
        WireMock.stubFor(get(urlEqualTo("/pet"))
                .willReturn(aResponse().withStatus(200).withBodyFile("response2.json")));
    }
}