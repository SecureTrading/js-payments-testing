package util.enums.responses;

public enum ApplePayResponse {
    SUCCESS("appleSuccess.json"),
    APPLE_AUTH_SUCCESS("appleAuthSuccess.json"),
    ERROR("appleAuthError.json"),
    CANCEL("appleCancel.json"),
    DECLINE("appleAuthError.json");

    private String mockJson;

    ApplePayResponse(String mockJson) {
        this.mockJson = mockJson;
    }

    public String getMockJson() {
        return this.mockJson;
    }
}
