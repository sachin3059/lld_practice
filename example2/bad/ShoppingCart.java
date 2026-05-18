package example2.bad;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {
    private List<Map<String, Object>> items = new ArrayList<>();
    private String customerEmail;
    private String customerPhone;
    private String couponCode;

    public ShoppingCart(String customerEmail, String customerPhone){
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
    }

    // Add item to the cart
    public void addItem(String name, double price, int quantity, String category){
        if(price < 0){
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if(quantity < 0){
            throw new IllegalArgumentException("Quantitity cannot be negative");
        }
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name required");
        }

        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("price", price);
        item.put("quantity", quantity);
        item.put("category", category);
        items.add(item);
    }


    // Applies a coupon
    public void applyCoupon(String code){
        this.couponCode = code;
    }

    // Calculate the total with tax and discount
    public double calculateTotal(){
        double subTotal = 0;
        for(Map<String, Object> item : items){
            double price = (double) item.get("price");
            int qty = (int)item.get("quantity");
            subTotal += price * qty;
        }

        // Apply coupon discount
        double discount = 0;
        if("SAVE10".equals(couponCode)){
            discount = subTotal * 0.10;
        }
        else if("SAVE20".equals(couponCode)){
            discount = subTotal * 0.20;
        }
        else if("FLAT100".equals(couponCode)){
            discount = 100;
        }

        // Apply GST    
        double afterDiscount = subTotal - discount;
        double gst = afterDiscount * 0.18;

        return subTotal + gst;
    }


    // Print invoice to console
    public void printInvoice(){
        System.out.println("=========== INVOICE =========");
        System.out.println("Customer: " + customerEmail);
        System.out.println("Date: " + new Date());
        System.out.println("--------------------------");
        for(Map<String, Object> item : items){
            System.out.println(item.get("name") + " x " + item.get("quantity") + " = " + (double)item.get("price") * (int)item.get("quantity")) ;
        }

        if(couponCode != null){
            System.out.println("Coupon applied: " + couponCode);
        }
        System.out.println("Total (incl. GST " + calculateTotal());
        System.out.println("=====================================");
    }


    // Save order to the database
    public void saveOrder(){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop", "root", "password" );
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO orders (email, total, date) VALUES(?, ?, ?");
                stmt.setString(1, customerEmail);
                stmt.setDouble(2, calculateTotal());
                stmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));
                stmt.executeUpdate();
                conn.close();
        } catch (Exception e) {
            System.out.println("DB error: " + e.getMessage() );
        }
    }

    // Process Payment via Razorpay
    public boolean processPayment(String cardNumber, String cvv, String expiry){
        // Pretend we are calling Razorpay api
        System.out.println("Charging card " + cardNumber + " for " + calculateTotal());
        if(cardNumber.length() != 16){
            System.out.println("Invalid card");
            return false;
        }
        // Simulate API call
        System.out.println("Payment successful via Razorpay");
        return true;
    }

    // Sends confirmation email
    public void sendConfirmationEmail(){
        // SMTP setup
        System.out.println("Connecting to smtp.google.com ....");
        System.out.println("Sending email to " + customerEmail);
        System.out.println("Subject: Order Confirmation");
        System.out.println("Body: Thank you for your order! Total: " + calculateTotal());
    }

    // Sends SMS
    public void sendConfirmationSMS(){
        // Twilio API call
        System.out.println("Calling Twilio API ....");
        System.out.println("SMS sent to " + customerPhone + " : order confirmed");
    }

    // Logs to file
    public void logTransaction(){
        try {
            FileWriter fw = new FileWriter("transaction.log", true);
            fw.write("[" + new Date() + "] Order placed by " + customerEmail + " for amount " + calculateTotal() + "\n");
            fw.close();
        } catch (Exception e) {
            System.out.println("Logging failed");
        }
    }


    // Full checkout flow
    public void checkout(String cardNumber, String cvv, String expiry){
        if(items.isEmpty()){
            System.out.println("Cart is empty");
            return;
        }
        boolean paid = processPayment(cardNumber, cvv, expiry);
        if(paid){
            saveOrder();
            printInvoice();
            sendConfirmationEmail();
            sendConfirmationSMS();
            logTransaction();
        }
        else{
            System.out.println("Checkout failed");
        }
    }
}
