public class ATM{
    private final Bank bank;
    private final CashDispenser cashDispenser;
    private ATMState currentState;
    private Card currentCard;
    private Account currentAccount;

    public ATM(Bank bank, CashDispenser cashDispenser){
        this.bank = bank;
        this.cashDispenser = cashDispenser;
        this.currentState = new IdleState(this);
        this.currentCard  = null;
        this.currentAccount = null;
    }

    public void insertCard(Card card){
        currentState.insertCard(card);
    }

    public void enterPin(int pin){
        currentState.enterPin(pin);
    }

    public void selectOperation(Transaction transaction){
        currentState.selectOperation(transaction);
    }

    public void ejectCard(){
        currentState.ejectCard();
    }


    // getters
    public Bank getBank() {
        return bank;
    }

    public CashDispenser getCashDispenser() {
        return cashDispenser;
    }

    public ATMState getCurrentState() {
        return currentState;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentState(ATMState currentState) {
        this.currentState = currentState;
    }

    public void setCurrentCard(Card currentCard) {
        this.currentCard = currentCard;
    }

    public void setCurrentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
    }
}