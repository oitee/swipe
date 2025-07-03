package otee.dev.swipe.model;

import java.time.OffsetDateTime;

public class ExpenseWithUsername {
    private Long paidBy;
    private double amount;
    private String description;
    private OffsetDateTime expenseTs;
    private String username;
    public ExpenseWithUsername(){}

    public Long getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(Long paidBy) {
        this.paidBy = paidBy;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OffsetDateTime getExpenseTs() {
        return expenseTs;
    }

    public void setExpenseTs(OffsetDateTime expenseTs) {
        this.expenseTs = expenseTs;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
