package util;

        import static com.github.tomakehurst.wiremock.client.WireMock.*;
        import static util.PropertiesHandler.getProperty;

        import com.github.tomakehurst.wiremock.client.WireMock;
        import util.enums.PropertyType;

public class MocksHandler {

    public static void stubPaymentStatus(PropertyType mockUrl, String mockJson) {
        WireMock.configureFor("https", "localhost", Integer.parseInt(getProperty(PropertyType.PORT)));
        WireMock.stubFor(get(urlEqualTo(getProperty(mockUrl)))
                .willReturn(aResponse().withStatus(200).withBodyFile(mockJson)));
    }
}

