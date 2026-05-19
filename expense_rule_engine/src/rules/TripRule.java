package expense_rule_engine.src.rules;

import java.util.List;
import java.util.Optional;

import expense_rule_engine.src.models.Expense;

public interface TripRule {
    Optional<List<Violation>> check(List<Expense> expenses);
}
