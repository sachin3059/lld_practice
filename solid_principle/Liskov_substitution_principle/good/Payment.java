// Base contract - only what all payments truly share

interface Payment {
    String getPaymentType();
    void generateReceipt();
}


// Extend contract - only for online payment
interface OnlinePayment extends Payment {
    void processOnline(double amount);
    void refund(double amount);
}


class UPIPayment implements OnlinePayment{
    void processOnline(double amount);
    void refund(double amount);
    String getPaymentType() return "UPI";
}

class CashOnDelivery implements Payment {
    //only implements what it truly supports
    String getPaymentType(){
        return "COD";
    }

    void generateReceipt(){

    }

    // ProcessOnline and refund are NOT in payment
    // COD is never forced to implement them
}



void ChargeCustomer(OnlinePayment p, double amount){
    p.processOnline(amount);
}