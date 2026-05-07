class PaymentProcessor {
    public void processPayment(String type) {
        if (type.equals("CREDIT_CARD")) {
            // credit card logic
        } else if (type.equals("UPI")) {
            // UPI logic
        } else if (type.equals("PAYPAL")) {
            // paypal logic
        }
        // every new payment method = modify this class
        // risk of breaking existing payment methods
    }
}