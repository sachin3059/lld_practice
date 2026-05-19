package expense_rule_engine.src.models;

// Ideally made using a builder pattern
public class Expense {
    private final String expenseId;
    private final String tripId;
    private final Double amountUsd;
    private final ExpenseType expenseType;

    public Expense(String expenseId, String tripId, Double amoundUsd, ExpenseType expenseType){
        this.expenseId = expenseId;
        this.tripId = tripId;
        this.amountUsd = amoundUsd;
        this.expenseType = expenseType;
    }

    public String getExpenseId(){
        return expenseId;
    }

    public String getTripId(){
        return tripId;
    }

    public Double getAmoundUsd(){
        return amountUsd;
    }

    public ExpenseType getExpenseType(){
        return expenseType;
    }
}
