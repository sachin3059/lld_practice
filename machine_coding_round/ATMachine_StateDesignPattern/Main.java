package machine_coding_round.ATMachine_StateDesignPattern;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();

        Card card1 = new Card("CARD123", 1234);
        Accound account1 = new Account("ACC001", 5000);
        bank.addCardAndAccount(card1, account1);

        Card card2 = new Card("CARD456", 5678);
        Account account2 = new Account("ACC002", 10000);

        CashDispenser cashDispenser = new CashDispenser();
        cashDispenser.addNotes(2000, 5);
        cashDispenser.addNotes(500, 10);
        cashDispenser.addNotes(100, 50);


        ATM atm = new ATM(bank, cashDispenser);

        atm.insert(card1);
        atm.enterpin(1234);
        atm.selectOperation(new BalanceInqueryTransaction("TXN001", account1));

        atm.selectOperation(new withdrawTransaction("TXN002", account1, 3700, cashDispenser));

        atm.selectOperation(new DepositTransaction("TXN003", account1, 1000));

        atm.selectOperation(new BalanceInqueryTransaction("TXN004", account1));

        atm.ejectCard();
    }
}
