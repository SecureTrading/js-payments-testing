package util;

        import static com.github.tomakehurst.wiremock.client.WireMock.*;

        import com.github.tomakehurst.wiremock.client.WireMock;

public class MocksHandler {

    public static void stubCreditCardSuccessfulPayment() {
        WireMock.configureFor("localhost", 8760);
        WireMock.stubFor(get(urlEqualTo("/pet"))
                .willReturn(aResponse().withStatus(200).withBodyFile("creditCardSuccessfulPayment.json")));
    }

    public static void stubCreditCardDeclinedPayment() {
        WireMock.configureFor("localhost", 8760);
        WireMock.stubFor(get(urlEqualTo("/pet"))
                .willReturn(aResponse().withStatus(200).withBodyFile("creditCardDeclinedPayment.json")));
    }
}

