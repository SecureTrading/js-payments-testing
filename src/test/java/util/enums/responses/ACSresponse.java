package util.enums.responses;

public enum ACSresponse {
    OK("ccACSoK.json"),
    NOACTION("ccACSnoaction.json"),
    FAILURE("ccACSfailure.json"),
    ERROR("ccACSerror.json");

    private String mockJson;

    ACSresponse(String mockJson) {
        this.mockJson = mockJson;
    }

    public String getMockJson() {
        return this.mockJson;
    }
}
