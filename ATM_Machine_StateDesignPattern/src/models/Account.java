package models;

public class Account{
    private final String accountNumber;
    private double balance;

    public Account(String accountNumber, double balance){
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public synchronized void deposit(double amount){
        if(amount <= 0){
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }
        this.balance += amount;
    }

    public synchronized boolean withdraw(double amount){
        if(amount <= 0){
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }
        if(amount > balance){
            return false;
        }
        this.balance -= amount;
        return true;
    }


    public String getAccountNumber(){
        return this.accountNumber;
    }

    public synchronized double getBalance(){
        return this.balance;
    }
}