package util.enums.responses;

public enum TDQresponsne {
    ENROLLED_Y("ccTDQEnrolledY.json"),
    NOT_ENROLLED_N("ccTDQEnrolledN.json"),
    NOT_ENROLLED_U("ccTDQEnrolledU.json"),
    INVALID_ACQUIRER("ccTDQInvalidAcquirer.json");

    private String mockJson;

    TDQresponsne(String mockJson) {
        this.mockJson = mockJson;
    }

    public String getMockJson() {
        return this.mockJson;
    }
}
