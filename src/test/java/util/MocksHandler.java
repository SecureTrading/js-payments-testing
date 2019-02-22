package util;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class MocksHandler {

    public static WireMockServer wireMockServer;

    public static void init() {
        wireMockServer = new WireMockServer(wireMockConfig().port(8760));
        wireMockServer.start();
    }

    private static void startWireMockServer() {
        init();
    }

    public static void stopWireMockServer() {
        wireMockServer.stop();
}

    public static void stubVersion1() {
        startWireMockServer();
        wireMockServer.stubFor(get(urlEqualTo("/pet"))
                .willReturn(aResponse().withStatus(200).withBody("Hello world version 1")));
    }

    public static void stubVersion2() {
        startWireMockServer();
        wireMockServer.stubFor(get(urlEqualTo("/pet"))
                .willReturn(aResponse().withStatus(200).withBody("Hello world version 2")));
    }
}