package expense_rule_engine.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import expense_rule_engine.models.Expense;
import expense_rule_engine.models.ExpenseType;
import expense_rule_engine.services.RuleEngine;
import expense_rule_engine.services.rules.ExpenseRule;
import expense_rule_engine.services.rules.TripRule;
import expense_rule_engine.services.rules.Violation;

public class SimpleRuleEngine implements RuleEngine {
    
    @Override
    public List<Violation> evaluate( List<Expense> expenses, Map<ExpenseType, List<ExpenseRule>> expenseRuleRegistry, List<ExpenseRule> allExpenseRulesRegistry, List<TripRule> tripRuleRegistry){

        List<Violation> violationsResult = new ArrayList<>();

        //1. check all expenses againt all expense rules

        for(Expense expense : expenses){
            // fetch all the rules for the expense type and if no rules found , then we have empty list
            List<ExpenseRule> rules = expenseRuleRegistry.getOrDefault(expense.getExpenseType(), List.of()); 

            for(ExpenseRule rule : rules){
                Optional<Violation> violation = rule.check(expense);
                if(violation.isPresent()){
                    violationsResult.add(violation.get());
                }
            }

            for(ExpenseRule rule : allExpenseRulesRegistry){
                Optional<Violation> violation = rule.check(expense);
                    if(violation.isPresent()){
                        violationsResult.add(violation.get());
                    }
            }
        }

        //2. check all expense against all the trip rules

        for(TripRule rule : tripRuleRegistry){
            Optional<Violation> violation = rule.check(expenses);
            if(violation.isPresent()){
                violationsResult.add(violation.get());
            }
        }

        return violationsResult;
    }
}
