package transactions;

import enums.TransactionStatus;
import models.Account;

public class DepositTransaction extends Transaction {
    private final double amount;

    public DepositTransaction(String transactionId, Account account, double amount) {
        super(transactionId, account);
        this.amount = amount;
    }

    @Override
    public void execute() {
        getAccount().deposit(amount);
        System.out.println("Amount deposited to you account: " + amount);
        this.setTransactionStatus(TransactionStatus.SUCCESS);
    }
}
