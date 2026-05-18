// The SMS Tool

class SMSSender {
    void sendSMS(String phone, String message){
        System.out.println("SMS sent to " + phone + ": " + message);
    }
}

// The order Service - directly creates and uses SMSSender

class OrderService {
    private SMSSender smsSender = new SMSSender();  // This is the problem;

    void placeOrder(String item, String phone){
        System.out.println("Order placed: " + item);
        smsSender.sendSMS(phone, "Your order is confirmed");
    }

}


// problems:

// -> product manager says "add Email notificaion" => you have to open orderService and edit it.
// -> product manager says "add whatsapp notificaion" => you have to open orderservice and edit it.


// -> so business logic is changing for infrastructure reason