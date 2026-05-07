package com.library.models;

import java.util.UUID;

/**
 * Fine is generated when a Loan is returned after dueDate.
 * Strategy pattern: FineCalculator is injected so the rate can change.
 */
public class Fine {
    private final String fineId;
    private final Loan loan;
    private final double amount;
    private boolean isPaid;

    public Fine(Loan loan, double amount) {
        this.fineId  = UUID.randomUUID().toString();
        this.loan    = loan;
        this.amount  = amount;
        this.isPaid  = false;
    }

    public String  getFineId()  { return fineId; }
    public Loan    getLoan()    { return loan; }
    public double  getAmount()  { return amount; }
    public boolean isPaid()     { return isPaid; }
    public void    pay()        { this.isPaid = true; }

    @Override
    public String toString() {
        return String.format("Fine[id=%s, amount=%.2f, paid=%s]",
            fineId, amount, isPaid);
    }
}


