package otee.dev.swipe.model;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.stream.Stream;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {
    List<Expense> findAllByGroupId(Long groupId);
    @Query("""
            SELECT
            expenses.description,
            expenses.amount,
            expenses.paid_by,
            expenses.expense_ts,
            users.username
            FROM expenses
            INNER JOIN users
            ON expenses.paid_by = users.id
            WHERE expenses.group_id = :groupId
            """)
    List<ExpenseWithUsername> findAllByGroupIdWithUsername(Long groupId);
}
