package transactions;

import enums.TransactionStatus;
import models.Account;

public class BalanceInquiryTransaction extends Transaction{

    public BalanceInquiryTransaction(String transactionId, Account account) {
        super(transactionId, account);
    }

    @Override
    public void execute(){
        double balance = getAccount().getBalance();
        System.out.println("Your current balance is: " + balance);
        this.setTransactionStatus(TransactionStatus.SUCCESS);
    }
}
