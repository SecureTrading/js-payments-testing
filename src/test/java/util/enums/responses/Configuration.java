package util.enums.responses;

public enum Configuration {
    CONFIG("config.json"),
    SUBMIT_ON_SUCCESS_TRUE("configSubmitOnSuccessTrue.json"),
    ANIMATED_CARD("configAnimatedCardTrue.json"),
    UPDATE_JWT("configUpdateJwtTrue.json"),
    FIELD_STYLE("configFieldStyle.json"),
    IMMEDIATE_PAYMENT("configImmediatePayment.json"),
    SKIP_JSINIT("configSkipJSinit.json"),
    DEFER_INIT_START_ON_LOAD("configStartOnLoadAndDeferInitTrue.json"),
    SUBMIT_CVV_ONLY("configSubmitCvvOnly.json");

    private String mockJson;

    Configuration(String mockJson) {
        this.mockJson = mockJson;
    }

    public String getMockJson() {
        return this.mockJson;
    }
}
