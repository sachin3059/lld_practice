package expense_rule_engine.src.impl;

import java.util.List;
import java.util.Optional;

import expense_rule_engine.src.models.Expense;
import expense_rule_engine.src.rules.TripRule;
import expense_rule_engine.src.rules.Violation;

public class TripTotalMaxRule implements TripRule {
    private final double maxAmount;
    
    public TripTotalMaxRule(double maxAmount){
        this.maxAmount = maxAmount;
    }

    @Override
    public Optional<Violation> check(List<Expense> expenses){
        double total = 0;
        for(Expense expense : expenses){
            total += expense.getAmoundUsd();
        }

        if(total > maxAmount){
            return Optional.of(Violation.of("Trip total exceeds the maximum amount"));
        }

        return Optional.empty();
    }
}
