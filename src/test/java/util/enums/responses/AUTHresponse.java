package util.enums.responses;

public enum AUTHresponse {
    OK("ccAUTHoK.json"),
    INVALID_FIELD("ccAUTHInvalidField.json"),
    SOCKET_ERROR("ccAUTHSocketError.json"),
    UNAUTHENTICATED("ccAUTHUnauthenticated.json"),
    DECLINE("ccAUTHDeclineError.json"),
    UNKNOWN_ERROR("ccAUTHUnknownError.json"),
    MERCHANT_DECLINE("ccAUTHMerchantDeclineError.json");

    private String mockJson;

    AUTHresponse(String mockJson) {
        this.mockJson = mockJson;
    }

    public String getMockJson() {
        return this.mockJson;
    }
}
