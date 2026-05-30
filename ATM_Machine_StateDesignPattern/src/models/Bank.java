package models;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<String, Account> cardToAccountMap;

    public Bank() {
        this.cardToAccountMap = new HashMap<String, Account>();
    }

    public void addCardToAccount(Card card, Account account){
        this.cardToAccountMap.put(card.getCardNumber(), account);
    }

    public boolean authenticate(Card card, int pin){
        if(!cardToAccountMap.containsKey(card.getCardNumber())){
            return false;
        }
        return card.validatePin(pin);
    }

    public Account getAccount(String cardNumber){
        Account account = this.cardToAccountMap.get(cardNumber);

        if(account == null){
            throw new IllegalArgumentException("Account does not exist");
        }
        return account;
    }
}
