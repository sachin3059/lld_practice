package machine_coding_round.ATMMachine_StateDesignPattern.transaction;

public class DepositTransaction extends Transaction {
    private int amount;

    public DepositTransaction(String transactionId, Account account, int amount) {
        super(transactionId, account);
        this.amount = amount;
    }

    @Override
    public void execute(){
        this.getAccount().deposit(amount);
        System.out.println("Amount deposited to the account: " + amount);
        this.setStatus(TransactionStatus.SUCCESS);
    }
}