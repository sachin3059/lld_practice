package states;

import models.Card;
import orchestrator.ATM;
import transactions.Transaction;

public class AuthenticatedState implements ATMState {
    private final ATM atm;

    public AuthenticatedState(ATM atm) {
        this.atm = atm;
    }

    @Override
    public void insertCard(Card card) {
        System.out.println("Card is already inserted");
    }

    @Override
    public void enterPin(int pin) {
        System.out.println("Card is already authenticated, do operations");
    }

    @Override
    public void selectOperation(Transaction transaction) {
        if(transaction == null){
            System.out.println("Invalid transaction");
            return;
        }
        transaction.execute();
    }

    @Override
    public void ejectCard() {
        atm.setCurrentCard(null);
        atm.setCurrentAccount(null);
        atm.setCurrentState(new IdleState(atm));
        System.out.println("Card has been ejected");
    }
}
