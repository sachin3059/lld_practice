class Payment {
    void processOnline(double amount){
        // charges customer's card or upi
    }

    void refund(double amount){
        // sends money back to source
    }
}

class UPIPayment extends Payment {
    void processOnline(double amount){
        // works
    }
    void refund(double amount){
        // works
    }
}


class CashOnDelivery extends Payment {
    void processOnline(double amount){
        // throw new UnsupportedOperationException("COD has NO online payment")
    }

    void refund(double amount){
        // throw new UnsupportedOperationException("COD refund is manual")
    }
}