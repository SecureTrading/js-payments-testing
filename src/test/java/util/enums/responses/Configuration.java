package util.enums.responses;

public enum Configuration {
    CONFIG("config.json"),
    SUBMIT_ON_SUCCESS_TRUE("submitOnSuccessTrue.json"),
    ANIMATED_CARD("animatedCardTrue.json"),
    UPDATE_JWT("updateJwtTrue.json"),
    FIELD_STYLE("fieldStyle.json");

    private String mockJson;

    Configuration(String mockJson) {
        this.mockJson = mockJson;
    }

    public String getMockJson() {
        return this.mockJson;
    }
}
