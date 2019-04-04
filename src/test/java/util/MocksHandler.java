package util;

        import static com.github.tomakehurst.wiremock.client.WireMock.*;
        import static util.PropertiesHandler.getProperty;

        import com.github.tomakehurst.wiremock.client.WireMock;
        import util.enums.PropertyType;

public class MocksHandler {

    public static void stubCcSuccessPaymentPayment() {
        WireMock.configureFor("https", "localhost", Integer.parseInt(getProperty(PropertyType.PORT)));
        WireMock.stubFor(get(urlEqualTo(getProperty(PropertyType.CC_MOCK_URI)))
                .willReturn(aResponse().withStatus(200).withBodyFile("ccSuccess.json")));
    }

    public static void stubCcFieldErrorPayment() {
        WireMock.configureFor("https", "localhost", Integer.parseInt(getProperty(PropertyType.PORT)));
        WireMock.stubFor(get(urlEqualTo(getProperty(PropertyType.CC_MOCK_URI)))
                .willReturn(aResponse().withStatus(200).withBodyFile("ccFieldError.json")));
    }

    public static void stubCcDeclineErrorPayment() {
        WireMock.configureFor("https", "localhost", Integer.parseInt(getProperty(PropertyType.PORT)));
        WireMock.stubFor(get(urlEqualTo(getProperty(PropertyType.CC_MOCK_URI)))
                .willReturn(aResponse().withStatus(200).withBodyFile("ccDeclineError.json")));
    }

    public static void stubCcSocketReceiveErrorPayment() {
        WireMock.configureFor("https", "localhost", Integer.parseInt(getProperty(PropertyType.PORT)));
        WireMock.stubFor(get(urlEqualTo(getProperty(PropertyType.CC_MOCK_URI)))
                .willReturn(aResponse().withStatus(200).withBodyFile("ccSocketError.json")));
    }

    public static void stubVisaSuccessPayment() {
        WireMock.configureFor("https", "localhost", Integer.parseInt(getProperty(PropertyType.PORT)));
        WireMock.stubFor(get(urlEqualTo(getProperty(PropertyType.VISA_MOCK_URI)))
                .willReturn(aResponse().withStatus(200).withBodyFile("visaSuccess.json")));
    }

    public static void stubVisaErrorPayment() {
        WireMock.configureFor("https", "localhost", Integer.parseInt(getProperty(PropertyType.PORT)));
        WireMock.stubFor(get(urlEqualTo(getProperty(PropertyType.VISA_MOCK_URI)))
                .willReturn(aResponse().withStatus(200).withBodyFile("visaError.json")));
    }

    public static void stubVisaCancelPayment() {
        WireMock.configureFor("https", "localhost", Integer.parseInt(getProperty(PropertyType.PORT)));
        WireMock.stubFor(get(urlEqualTo(getProperty(PropertyType.VISA_MOCK_URI)))
                .willReturn(aResponse().withStatus(200).withBodyFile("visaCancel.json")));
    }
}

