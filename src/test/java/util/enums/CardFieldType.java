package util.enums;

public enum CardFieldType {
    number("number"),
    cvc("cvc"),
    expiryDate("expiryDate");

    private String text;

    CardFieldType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static CardFieldType fromString(String text) {
        for (CardFieldType cardFieldType : CardFieldType.values()) {
            if (cardFieldType.text.equalsIgnoreCase(text)) {
                return cardFieldType;
            }
        }
        return null;
    }
}



