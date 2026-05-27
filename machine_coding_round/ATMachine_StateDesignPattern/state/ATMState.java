package machine_coding_round.ATMachine_StateDesignPattern.state;

public interface ATMState {
    void insertCard(Card card);
    void enterPin(int pin);
    void selectOperation(Transaction transaction);
    void ejectCard();
} 