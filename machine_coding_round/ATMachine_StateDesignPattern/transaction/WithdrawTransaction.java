package machine_coding_round.ATMMachine_StateDesignPattern.transaction;

public class WithdrawTransaction extends Transaction {
    private int amount;
    private final CashDispenser cashDispenser;

    public WithdrawTransaction(String transactionId, Account account, int amount, CashDispenser cashDispenser){
        super(transactionId, account);
        this.amount = amount;
        this.cashDispenser = cashDispenser;
    }

    @Override
    public void execute(){
        boolean debited = getAccount().withdraw(amount);
        if(!debited){
            setStatus(TransactionStatus.FAILED);
            System.out.println("Insufficient account balance");
            return;
        }

        Map<Integer, Integer> notes = cashDispenser.dispense(amount);
        if(notes == null){
            getAccount().deposit(amount);
            setStatus(TransactionStatus.FAILED);
            System.out.println("ATM cannot dispense this amount. REFUNDED");
            return;
        }

        setStatus(TransactionStatus.SUCCESS);
        System.out.println("Dispensed: " + notes);
    }
}