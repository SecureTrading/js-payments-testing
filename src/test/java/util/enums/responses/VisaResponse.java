package util.enums.responses;

public enum VisaResponse {
    VISA_AUTH_SUCCESS("visaAuthSuccess.json"),
    SUCCESS("visaSuccess.json"),
    ERROR("visaError.json"),
    CANCEL("visaCancel.json");

    private String mockJson;

    VisaResponse(String mockJson) {
        this.mockJson = mockJson;
    }

    public String getMockJson() {
        return this.mockJson;
    }
}
