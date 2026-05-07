package com.library.patterns;

import com.library.models.Loan;

/** Flat â‚¹5 per overdue day. Default strategy. */
public class FlatRateFineCalculator implements FineCalculator {
    private static final double FINE_PER_DAY = 5.0;

    @Override
    public double calculate(Loan loan) {
        long overdueDays = loan.overdueDays();
        return overdueDays * FINE_PER_DAY;
    }
}


// â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
