package expense_rule_engine.services;

import java.util.List;
import java.util.Map;

import expense_rule_engine.models.Expense;
import expense_rule_engine.models.ExpenseType;
import expense_rule_engine.services.rules.ExpenseRule;
import expense_rule_engine.services.rules.TripRule;
import expense_rule_engine.services.rules.Violation;

public interface RuleEngine {
    List<Violation> evaluate( List<Expense> expenses, Map<ExpenseType, List<ExpenseRule>> expenseRuleRegistry, List<ExpenseRule> allExpenseRulesRegistry, List<TripRule> tripRuleRegistry);
}
