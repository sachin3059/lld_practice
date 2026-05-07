interface Notifier {
    void notify(String contact, String message);
}


class SMSSender implements Notifier{
    public void notify(String contact, String message){
        System.out.println("SMS to " + contact + ": " + message);
    }
}

class EmailSender implements Notifier {
    public void notify(String contact, String message) {
        System.out.println("Email to " + contact + ": " + message);
    }
}

class WhatsAppSender implements Notifier {
    public void notify(String contact, String message){
        System.out.printlin("WhatsApp to " + contact + ": " + message);
    }
}


class OrderService {
    private final Notifier notifier; // interface - not SMSSender , not EmailSender // OrderService has not IDEA which one it is

    OrderService (Notifier notifier){
        this.notifier = notifier;
    }


    void placeOrder(String item, String contact){
        System.out.println("Order placed: " + item);
        notifier.notify(contact, "Your order is confirmed");
    }
}




