package com.library.patterns;

import com.library.models.Loan;

/**
 * Strategy Pattern: FineCalculator.
 * Swap implementations without changing LibraryService.
 *
 * Interview talking point: "I used Strategy here so the fine
 * policy can change (flat, tiered, waived) without modifying
 * the service class â€” open/closed principle."
 */
public interface FineCalculator {
    double calculate(Loan loan);
}


// â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
