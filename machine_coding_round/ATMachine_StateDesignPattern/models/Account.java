package machine_coding_round.ATMachine_StateDesignPattern.models;

public class Account {
    private final String accountNumber;
    private double balance;

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.balance += amount;
    }

    public boolean withdraw(double amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if(this.balance < amount){
            return false;
        }
        this.balance -= amount;
        return true;
    }
}