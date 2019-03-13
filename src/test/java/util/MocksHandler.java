package util;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.client.WireMock;

public class MocksHandler {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().httpsPort(8443));


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
