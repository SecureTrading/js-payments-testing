package util.enums;

public enum PaymentType {
    visaCheckout ("visaCheckout");

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
