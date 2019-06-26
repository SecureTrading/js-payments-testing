package util;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static util.PropertiesHandler.getProperty;

import com.github.tomakehurst.wiremock.client.WireMock;
import util.enums.PropertyType;
import util.enums.RequestType;

public class MocksHandler {

        public static void configureForLocalhost() {
                WireMock.configureFor("https", "webservices.securetrading.net",
                                Integer.parseInt(getProperty(PropertyType.PORT)));
        }

        public static void stubSTRequestType(String mockJson, RequestType requestType) {
                MocksHandler.stubUrlOptionsForCors(PropertyType.GATEWAY_MOCK_URI);
                MocksHandler.configureForLocalhost();
                WireMock.stubFor(post(urlEqualTo(getProperty(PropertyType.GATEWAY_MOCK_URI)))
                                .withRequestBody(containing(requestType.toString()))
                                .willReturn(aResponse().withStatus(200).withHeader("Access-Control-Allow-Origin", "*")
                                                .withHeader("Access-Control-Allow-Headers", "Content-Type")
                                                .withHeader("Access-Control-Allow-Methods", "GET, POST")
                                                .withBodyFile(mockJson)));
        }

        public static void stubSTRequestTypeServerError(RequestType requestType) {
                MocksHandler.stubUrlOptionsForCors(PropertyType.GATEWAY_MOCK_URI);
                MocksHandler.configureForLocalhost();
                WireMock.stubFor(post(urlEqualTo(getProperty(PropertyType.GATEWAY_MOCK_URI)))
                                .withRequestBody(containing(requestType.toString()))
                                .willReturn(aResponse().withStatus(500).withHeader("Access-Control-Allow-Origin", "*")
                                                .withHeader("Access-Control-Allow-Headers", "Content-Type")
                                                .withHeader("Access-Control-Allow-Methods", "GET, POST")));
        }

        public static void stubUrlOptionsForCors(PropertyType mockUrl) {
                MocksHandler.configureForLocalhost();
                WireMock.stubFor(options(urlEqualTo(getProperty(mockUrl)))
                                .willReturn(aResponse().withStatus(200).withHeader("Access-Control-Allow-Origin", "*")
                                                .withHeader("Access-Control-Allow-Headers", "Content-Type")
                                                .withHeader("Access-Control-Allow-Methods", "GET, POST").withBody("")));
        }

        public static void stubPaymentStatus(PropertyType mockUrl, String mockJson) {
                MocksHandler.stubUrlOptionsForCors(mockUrl);
                MocksHandler.configureForLocalhost();
                WireMock.stubFor(get(urlEqualTo(getProperty(mockUrl))).willReturn(aResponse().withStatus(200)
                                .withHeader("Access-Control-Allow-Origin", "*")
                                .withHeader("Access-Control-Allow-Headers", "Content-Type")
                                .withHeader("Access-Control-Allow-Methods", "GET, POST").withBodyFile(mockJson)));
        }
}
