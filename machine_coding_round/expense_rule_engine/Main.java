package expense_rule_engine;

import java.util.ArrayList;
import java.util.List;

import expense_rule_engine.models.Expense;
import expense_rule_engine.models.ExpenseType;
import expense_rule_engine.registry.RuleRegistry;
import expense_rule_engine.services.impl.SimpleRuleEngine;
import expense_rule_engine.services.rules.Violation;

public class Main {
    public static void main(String[] args) {
        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense("1", "1", 10.0, ExpenseType.RESTAURANT));
        expenses.add(new Expense("2", "1", 50.0, ExpenseType.RESTAURANT));
        expenses.add(new Expense("2", "1", 100.0, ExpenseType.RESTAURANT));

        List<Violation> violations = new SimpleRuleEngine().evaluate(expenses, RuleRegistry.getExpenseRuleRegistry(), RuleRegistry.getAllExpenseRulesRegistry(), RuleRegistry.getAllTripRulesRegistry());

        for(Violation violation : violations){
            System.out.println(violation.getMessage());
        }
    }

    
}
