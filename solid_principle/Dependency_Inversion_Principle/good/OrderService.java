interface Notifier {
    void notify(String contact, String message);
}

class SMSSender implements Notifier {

    public void notify(String contact, String message) {
        System.out.println("SMS to " + contact + ": " + message);
    }
}

class EmailSender implements Notifier {

    public void notify(String contact, String message) {
        System.out.println("Email to " + contact + ": " + message);
    }
}

class WhatsAppSender implements Notifier {

    public void notify(String contact, String message) {
        System.out.println("WhatsApp to " + contact + ": " + message);
    }
}

public class OrderService {

    // Loose coupling using interface
    private final Notifier notifier;

    OrderService(Notifier notifier) {
        this.notifier = notifier;
    }

    void placeOrder(String item, String contact) {

        System.out.println("Order placed: " + item);

        notifier.notify(contact, "Your order is confirmed");
    }
}

class Main {

    public static void main(String[] args) {

        // SMS notification
        Notifier sms = new SMSSender();
        OrderService order1 = new OrderService(sms);

        order1.placeOrder("Laptop", "9876543210");

        System.out.println();

        // Email notification
        Notifier email = new EmailSender();
        OrderService order2 = new OrderService(email);

        order2.placeOrder("Phone", "abc@gmail.com");

        System.out.println();

        // WhatsApp notification
        Notifier whatsapp = new WhatsAppSender();
        OrderService order3 = new OrderService(whatsapp);

        order3.placeOrder("Headphones", "9999999999");
    }
}