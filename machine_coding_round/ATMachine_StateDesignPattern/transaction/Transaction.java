package machine_coding_round.ATMachine_StateDesignPattern.transaction;
import machine_coding_round.ATMachine_StateDesignPattern.enums.Transactionstatus;

public abstract  class  Transaction{
    private final String transactionId;
    private final Account account;
    private TransactionStatus status;

    public Transaction(String transactionId, Account account){
        this.transactionId = transactionId;
        this.account = account;
        this.status = TransactionStatus.PENDING;
    }

    public abstract void execute();

    public TransactionStatus getStatus(){
        return this.status;
    }

    public String getTransactionId(){
        return this.transactionId;
    }

    public Account getAccount(){
        return this.account;
    }

    protected void setStatus(TransactionStatus status){
        this.status = status;
    }
}