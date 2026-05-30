package transactions;

import enums.TransactionStatus;
import models.Account;

public abstract class Transaction {
    private final String transactionId;
    private final Account account;
    private TransactionStatus transactionStatus;

    public Transaction(String transactionId, Account account) {
        this.transactionId = transactionId;
        this.account = account;
        this.transactionStatus = TransactionStatus.PENDING;
    }

    public abstract void execute();

    public String getTransactionId() {
        return transactionId;
    }
    public Account getAccount() {
        return account;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}

