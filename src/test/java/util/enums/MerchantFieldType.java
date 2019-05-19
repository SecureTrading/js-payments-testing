package util.enums;

public enum MerchantFieldType {
    name("name"),
    email("email"),
    phone("phone");

    private String text;

    MerchantFieldType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static MerchantFieldType fromString(String text) {
        for (MerchantFieldType cardFieldType : MerchantFieldType.values()) {
            if (cardFieldType.text.equalsIgnoreCase(text)) {
                return cardFieldType;
            }
        }
        return null;
    }
}



