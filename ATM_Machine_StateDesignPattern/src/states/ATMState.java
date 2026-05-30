package states;

import models.Card;
import transactions.Transaction;

public interface ATMState {
    public void insertCard(Card card);
    public void enterPin(int pin);
    public void selectOperation(Transaction transaction);
    public void ejectCard();
}
