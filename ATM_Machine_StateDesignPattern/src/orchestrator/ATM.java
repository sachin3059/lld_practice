package orchestrator;

import models.Account;
import models.Bank;
import models.Card;
import models.CashDispenser;
import states.ATMState;
import states.IdleState;
import transactions.Transaction;

public class ATM {
    private final CashDispenser cashDispenser;
    private final Bank bank;

    private ATMState currentState;
    private Card currentCard;
    private Account currentAccount;

    public ATM(Bank bank, CashDispenser cashDispenser) {
        this.bank = bank;
        this.cashDispenser = cashDispenser;
        this.currentState = new IdleState(this);
        this.currentCard = null;
        this.currentAccount = null;
    }

    // user facing functions

    public void insertCard(Card card) {
        currentState.insertCard(card);
    }

    public void enterPin(int pin) {
        currentState.enterPin(pin);
    }

    public void selectOperation(Transaction transaction) {
        currentState.selectOperation(transaction);
    }

    public void ejectCard() {
        currentState.ejectCard();
    }


    // getters

    public Bank getBank() {
        return bank;
    }

    public CashDispenser getCashDispenser() {
        return cashDispenser;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public ATMState getCurrentState() {
        return currentState;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    // setters

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
