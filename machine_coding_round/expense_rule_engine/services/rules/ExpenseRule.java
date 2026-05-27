package expense_rule_engine.services.rules;

import java.util.Optional;

import expense_rule_engine.models.Expense;

public interface ExpenseRule {
    Optional<Violation> check(Expense e);
}

