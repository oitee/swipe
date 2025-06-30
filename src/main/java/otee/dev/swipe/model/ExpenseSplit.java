package otee.dev.swipe.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Table("expense_splits")
public class ExpenseSplit {
    @Id
    private Long id;
    private Long expenseId;
    private Long userId;
    private Double amount;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public ExpenseSplit(){}
    public ExpenseSplit(Long expenseId, Long userId, Double amount){
        this.expenseId = expenseId;
        this.userId = userId;
        this.amount = amount;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }
}
