package expense_rule_engine.src.registry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import expense_rule_engine.src.impl.DisallowRule;
import expense_rule_engine.src.impl.MaxAmountRule;
import expense_rule_engine.src.models.ExpenseType;
import expense_rule_engine.src.rules.ExpenseRule;

public class RuleRegistry {
    public static void getExpenseRuleRegistry(){
        Map<ExpenseType, List<ExpenseRule>> registry = new HashMap<>();

        registry.put(ExpenseType.RESTAURANT, List.of(
            new DisallowRule(),
            new MaxAmountRule(75)
        ));
        registry.put(ExpenseType.AIRFARE, List.of(
            new DisallowRule()
        ));
    }

    public static List<ExpenseRule> getAllExpenseRulesRegistry(){
        return List.of(
            new MaxAmountRule(250)
        );
    }
}


// 
