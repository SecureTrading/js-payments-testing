package util;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

public class MocksHandler {

    public static WireMockServer wireMockServer;

    public static void init() {
        wireMockServer = new WireMockServer(wireMockConfig().port(8760));
        wireMockServer.start();
    }

    public static void startWireMockServer() {
        if (wireMockServer == null) {
            System.out.println("**********************************- wiremock server started");
            init();
        }
    }

    public static void stopWireMockServer() {
        System.out.println("**********************************- wiremock server stopped");
        wireMockServer.stop();
//        wireMockServer = null;
    }

    public static void stubVersion1() {
        WireMock.configureFor("localhost", 8760);
        WireMock.stubFor(get(urlEqualTo("/pet"))
                .willReturn(aResponse().withStatus(200).withBodyFile("response1.json")));
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void stubVersion2() {
        WireMock.configureFor("localhost", 8760);
        WireMock.stubFor(get(urlEqualTo("/pet"))
                .willReturn(aResponse().withStatus(200).withBodyFile("response2.json")));
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}