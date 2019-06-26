package util.enums.responses;

public enum InvalidFieldResponse {
    CARD_NUMBER("numberInvalidField.json"),
    EXPIRY_DATE("expiryDateInvalidField.json"),
    CVC("cvvInvalidField.json"),
    EMAIL("emailInvalidField.json");

    private String mockJson;

    InvalidFieldResponse(String mockJson) {
        this.mockJson = mockJson;
    }

    public String getMockJson() {
        return this.mockJson;
    }
}
