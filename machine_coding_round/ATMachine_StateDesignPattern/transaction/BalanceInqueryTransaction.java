package machine_coding_round.ATMMachine_StateDesignPattern.transaction;

public class BalanceInqueryTransaction extends  Transaction {

    public BalanceInqueryTransaction(String transactionId, Account account){
        super(transactionId, account);
    }

    @Override
    public void execute(){
        int balance = this.getAccount().getBalance();
        System.out.println("Your current balance is: " + balance);
        this.setStatus(TransactionStatus.SUCCESS);
    }
}