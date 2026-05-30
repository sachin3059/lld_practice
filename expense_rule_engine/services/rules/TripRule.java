package expense_rule_engine.services.rules;

import java.util.List;
import java.util.Optional;

import expense_rule_engine.models.Expense;

public interface TripRule {
    Optional<Violation> check(List<Expense> expenses);
}
