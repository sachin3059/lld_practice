class PaymentProcessor {
    public void process (PaymentMethod method, double amount){
        method.processPayment(amount); // never changes
    }
}