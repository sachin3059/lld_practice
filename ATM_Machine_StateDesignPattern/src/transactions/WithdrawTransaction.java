package transactions;

import enums.TransactionStatus;
import models.Account;
import models.CashDispenser;

import java.util.Map;

public class WithdrawTransaction extends Transaction {
    private final int amount;
    private final CashDispenser cashDispenser;

    public WithdrawTransaction(String transactionId, Account account, int amount, CashDispenser cashDispenser) {
        super(transactionId, account);
        this.amount = amount;
        this.cashDispenser = cashDispenser;
    }

    @Override
    public void execute() {
        boolean debited = getAccount().withdraw(amount);
        if(!debited){
            System.out.println("Insufficient balance");
        }

        Map<Integer, Integer> notes = cashDispenser.dispense(amount);

        if(notes.isEmpty()){
            // we have to rollback
            // refund the amount SAGA pattern.

            getAccount().deposit(amount);
            this.setTransactionStatus(TransactionStatus.FAILED);
            System.out.println("orchestrator.ATM cannot dispense this amount, Refunded: " + amount);
            return;
        }

        this.setTransactionStatus(TransactionStatus.SUCCESS);
        System.out.println("Dispensed this amount: " + amount);
    }


}
