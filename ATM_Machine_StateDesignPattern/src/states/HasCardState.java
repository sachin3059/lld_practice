package states;

import models.Account;
import models.Bank;
import models.Card;
import orchestrator.ATM;
import transactions.Transaction;

public class HasCardState implements ATMState {
    private final ATM atm;

    public HasCardState(ATM atm) {
        this.atm = atm;
    }

    @Override
    public void insertCard(Card card) {
        System.out.println("A card is already inserted");
    }

    @Override
    public void enterPin(int pin) {
        Card card = atm.getCurrentCard();
        Bank bank = atm.getBank();

        if(bank.authenticate(card, pin)){
            Account account = bank.getAccount(card.getCardNumber());
            atm.setCurrentAccount(account);
            atm.setCurrentState(new AuthenticatedState(atm));
            System.out.println("PIN verified, now you can perform operations");
        }
        else{
            System.out.println("PIN invalid, please try again");
        }
    }

    @Override
    public void selectOperation(Transaction transaction) {
        System.out.println("Please enter your pin first");
    }

    @Override
    public void ejectCard() {
        atm.setCurrentCard(null);
        atm.setCurrentState(new IdleState(atm));
        System.out.println("Card ejected");
    }
}
