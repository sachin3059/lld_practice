package com.library.patterns;

import com.library.models.Loan;

/**
 * Tiered: â‚¹5/day for first 7 overdue days, â‚¹10/day after that.
 * Plugged in via constructor injection â€” no service code changes.
 */
public class TieredFineCalculator implements FineCalculator {
    private static final double BASE_RATE      = 5.0;
    private static final double ESCALATED_RATE = 10.0;
    private static final long   ESCALATION_DAY = 7;

    @Override
    public double calculate(Loan loan) {
        long days = loan.overdueDays();
        if (days <= 0) return 0;
        if (days <= ESCALATION_DAY) return days * BASE_RATE;
        return (ESCALATION_DAY * BASE_RATE)
             + ((days - ESCALATION_DAY) * ESCALATED_RATE);
    }
}


// â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
