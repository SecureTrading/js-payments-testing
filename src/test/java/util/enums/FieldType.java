package util.enums;

public enum FieldType {
    CARD_NUMBER("CARD_NUMBER"),
    CVC("CVC"),
    EXPIRY_DATE("EXPIRY_DATE"),
    ANIMATED_CARD("ANIMATED_CARD"),
    NOTIFICATION_FRAME("NOTIFICATION_FRAME"),
    EMAIL("EMAIL");

    private String text;

    FieldType(String text) {
        this.text = text;
    }

    public String getText() {
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



