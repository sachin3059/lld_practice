package expense_rule_engine.src.registry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import expense_rule_engine.src.models.ExpenseType;
import expense_rule_engine.src.services.rules.ExpenseRule;
import expense_rule_engine.src.services.rules.TripRule;
import expense_rule_engine.src.services.rules.impl.DisallowRule;
import expense_rule_engine.src.services.rules.impl.MaxAmountRule;
import expense_rule_engine.src.services.rules.impl.TripTotalMaxRule;

public class RuleRegistry {
    public static Map<ExpenseType, List<ExpenseRule>> getExpenseRuleRegistry(){
        Map<ExpenseType, List<ExpenseRule>> registry = new HashMap<>();

        registry.put(ExpenseType.RESTAURANT, List.of(
            new DisallowRule(),
            new MaxAmountRule(75)
        ));
        registry.put(ExpenseType.AIRFARE, List.of(
            new DisallowRule()
        ));

        return registry;
    }

    public static List<ExpenseRule> getAllExpenseRulesRegistry(){
        return List.of(
            new MaxAmountRule(250)
        );
    }

    public static List<TripRule> getAllTripRulesRegistry(){
        return List.of(new TripTotalMaxRule(1000));
    }
}


// 
