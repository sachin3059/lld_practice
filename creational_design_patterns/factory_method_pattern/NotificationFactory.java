package creational_design_patterns.factory_method_pattern;


// product interface
interface Notification {
    void send(String message);
}

// concreate products

class EmailNotification implements Notification {
    public void send(String message){
        System.out.println("Email send: " + message);
    }
}


class SMSNotification implements Notification{
    public void send(String message){
        System.out.println("SMS send: " +  message);
    }
}


class PushNotification implements Notification{
    public void send(String message){
        System.out.println("Push Notification sent: " + message);
    }
}



// Factory
public class NotificationFactory {
    public static Notification createNotification(String type){
        switch (type.toUpperCase()) {
            case "EMAIL": return new EmailNotification();
            case "SMS" : return new SMSNotification();
            case "PUSH" : return new PushNotification();
            default: throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}



// usage
// Notification n = NotificationFactory.createNotification("EMAIL");
// n.send("Hello!")
