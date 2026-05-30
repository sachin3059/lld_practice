import models.Account;
import models.Bank;
import models.Card;
import models.CashDispenser;
import orchestrator.ATM;
import transactions.BalanceInquiryTransaction;
import transactions.WithdrawTransaction;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        CashDispenser cashDispenser = new CashDispenser();

        Card card = new Card("card1", 1234);
        Account account = new Account("123456", 50000);
        bank.addCardToAccount(card, account);

        cashDispenser.addNote(500, 50);
        cashDispenser.addNote(100, 50);
        cashDispenser.addNote(200, 50);



        // orchestrator ATM

        ATM atm = new ATM(bank, cashDispenser);
//        atm.insertCard(card);
//        atm.enterPin(1234);
//        atm.selectOperation(new BalanceInquiryTransaction("TXN001", account));
//        atm.ejectCard();

        atm.insertCard(card);
        atm.enterPin(1234);
        atm.selectOperation(new WithdrawTransaction("TXN002", account, 5000, cashDispenser));


        atm.selectOperation(new BalanceInquiryTransaction("TXN003", account));
        atm.ejectCard();
    }
}