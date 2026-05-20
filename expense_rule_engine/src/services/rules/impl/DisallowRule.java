package expense_rule_engine.src.services.rules.impl;

import java.util.Optional;

import expense_rule_engine.src.models.Expense;
import expense_rule_engine.src.services.rules.ExpenseRule;
import expense_rule_engine.src.services.rules.Violation;

public class DisallowRule implements ExpenseRule  {
    
    @Override
    public Optional<Violation> check(Expense e){
        return Optional.of(Violation.of("Expense type " + e.getExpenseType() + " is not allowed"));
    }
}
