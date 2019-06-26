package util.enums;

public enum PaymentType {
    VISA_CHECKOUT("VISA_CHECKOUT"),
    APPLE_PAY("APPLE_PAY"),
    CARDINAL_COMMERCE("CARDINAL_COMMERCE");

    private String text;

    PaymentType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static PaymentType fromString(String text) {
        for (PaymentType paymentType : PaymentType.values()) {
            if (paymentType.text.equalsIgnoreCase(text)) {
                return paymentType;
            }
        }
        return null;
    }
}
