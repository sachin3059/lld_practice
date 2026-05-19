package expense_rule_engine.src.rules;

import java.util.Optional;

import expense_rule_engine.src.models.Expense;

public interface ExpenseRule {
    Optional<Violation> check(Expense e);
}

