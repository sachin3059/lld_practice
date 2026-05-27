package machine_coding_round.ATMachine_StateDesignPattern.models;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<String, Account> cardToAccountMap;

    public Bank(){
        this.cardToAccountMap = new HashMap<String , Account>();
    }

    public void addCardAndAccount(Card card, Account account){
        cardToAccountMap.put(card.getCardNumber(), account);
    }

    public boolean authenticate(Card card, int pin){
        if(!cardToAccountMap.containsKey(card.getCardNumber())){
            return false;
        }
        return card.validatePin(pin);
    }

    public Account getAccount(String cardNumber){
        Account account =  cardToAccountMap.get(cardNumber);
        if(account == null){
            throw new IllegalStateException("No account linked to card: " + cardNumber);
        }
        return account;
    }
}