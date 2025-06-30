package otee.dev.swipe.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Table("expenses")
public class Expense {
    @Id
    private Long id;
    private Long groupId;
    private Long paidBy;
    private double amount;
    private String description;
    private Boolean isSettled;
    private String splitType;
    private OffsetDateTime expenseTs;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;


    public Expense(){}

    public Expense(Long groupId, Long paidBy, double amount, String description){
        this.groupId = groupId;
        this.paidBy = paidBy;
        this.amount = amount;
        this.description = description;
        this.isSettled = false;
        this.splitType = "EQUAL";
        this.expenseTs = OffsetDateTime.now();
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
