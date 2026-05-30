package states;

import models.Card;
import orchestrator.ATM;
import transactions.Transaction;

public class IdleState implements ATMState{
    private final ATM atm;

    public IdleState(ATM atm){
        this.atm = atm;
    }
    @Override
    public void insertCard(Card card) {
        atm.setCurrentCard(card);
        atm.setCurrentState(new HasCardState(atm));
        System.out.println("Card inserted, please enter your pin");
    }

    @Override
    public void enterPin(int pin) {
        System.out.println("Please enter a card first");
    }

    @Override
    public void selectOperation(Transaction transaction) {
        System.out.println("Please enter a card first");
    }

    @Override
    public void ejectCard() {
        System.out.println("Please enter a card first");
    }
}
