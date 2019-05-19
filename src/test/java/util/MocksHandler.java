package util;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static util.PropertiesHandler.getProperty;

import com.github.tomakehurst.wiremock.client.WireMock;
import util.enums.PropertyType;

public class MocksHandler {

    public static void stubSTRequestType(String mockJson, String requestType) {
        String gatewayUrl = "/jwt/";// TODO add to property
        MocksHandler.stubUrlOptionsForCors(gatewayUrl);
        WireMock.configureFor("https", "localhost", Integer.parseInt(getProperty(PropertyType.PORT)));
        WireMock.stubFor(post(urlEqualTo(gatewayUrl)).withRequestBody(containing(requestType))
                .willReturn(aResponse().withStatus(200).withHeader("Access-Control-Allow-Origin", "*")
                        .withHeader("Access-Control-Allow-Headers", "*")
                        .withHeader("Access-Control-Allow-Methods", "GET, POST").withBodyFile(mockJson)));
    }

    public static void stubSTRequestTypeServerError(String requestType) {
        String gatewayUrl = "/jwt/";// TODO add to property
        MocksHandler.stubUrlOptionsForCors(gatewayUrl);
        WireMock.configureFor("https", "localhost", Integer.parseInt(getProperty(PropertyType.PORT)));
        WireMock.stubFor(post(urlEqualTo(gatewayUrl)).withRequestBody(containing(requestType))
                .willReturn(aResponse().withStatus(500)));
    }

    public static void stubUrlOptionsForCors(String mockUrl) {
        WireMock.configureFor("https", "localhost", Integer.parseInt(getProperty(PropertyType.PORT)));
        WireMock.stubFor(options(urlEqualTo(mockUrl)).willReturn(aResponse().withStatus(200)
                .withHeader("Access-Control-Allow-Origin", "*").withHeader("Access-Control-Allow-Headers", "*")
                .withHeader("Access-Control-Allow-Methods", "GET, POST").withBody("")));
    }

    public static void stubPaymentStatus(PropertyType mockUrl, String mockJson) {
        WireMock.configureFor("https", "localhost", Integer.parseInt(getProperty(PropertyType.PORT)));
        WireMock.stubFor(
                get(urlEqualTo(getProperty(mockUrl))).willReturn(aResponse().withStatus(200).withBodyFile(mockJson)));
    }
}
