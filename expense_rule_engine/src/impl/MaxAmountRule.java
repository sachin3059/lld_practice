package expense_rule_engine.src.impl;

import java.util.Optional;

import expense_rule_engine.src.models.Expense;
import expense_rule_engine.src.rules.ExpenseRule;
import expense_rule_engine.src.rules.Violation;

public class MaxAmountRule implements ExpenseRule {
    private final double maxAmount;

    public MaxAmountRule(double maxAmount){
        this.maxAmount = maxAmount;
    }

    @Override
    public Optional<Violation> check(Expense e){
        if(e.getAmoundUsd() > maxAmount){
            return Optional.of(Violation.of("Expense amount is greater than max amount"));
        }
        return Optional.empty();
    }
}
