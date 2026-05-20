package expense_rule_engine.services.rules.impl;

import java.util.Optional;

import expense_rule_engine.models.Expense;
import expense_rule_engine.services.rules.ExpenseRule;
import expense_rule_engine.services.rules.Violation;

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
