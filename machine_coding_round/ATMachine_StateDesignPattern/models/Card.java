package machine_coding_round.ATMachine_StateDesignPattern.models;

public class Card {
    private final String cardNumber;
    private final int pin;

    public Card(String cardNumber, int pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public boolean validatePin(int inputPin){
        return this.pin == inputPin;
    }
}
