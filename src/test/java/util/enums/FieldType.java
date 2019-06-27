package util.enums;

public enum FieldType {
    CARD_NUMBER("st-card-number-iframe"),
    CVC("st-security-code-iframe"),
    EXPIRY_DATE("st-expiration-date-iframe"),
    ANIMATED_CARD("st-animated-card-iframe"),
    NOTIFICATION_FRAME("st-notification-frame-iframe"),
    SUBMIT_BUTTON("");

    private String text;

    FieldType(String text) {
        this.text = text;
    }

    public String getIframeName() {
        return this.text;
    }

    public static FieldType fromString(String text) {
        for (FieldType fieldType : FieldType.values()) {
            if (fieldType.text.equalsIgnoreCase(text)) {
                return fieldType;
            }
        }
        return null;
    }
}



