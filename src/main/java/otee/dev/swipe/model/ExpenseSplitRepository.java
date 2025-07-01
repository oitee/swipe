package otee.dev.swipe.model;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExpenseSplitRepository extends CrudRepository<ExpenseSplit, Long> {
    @Query("SELECT * FROM expense_splits WHERE user_id = :userId AND expense_id IN (:expenseIds)")
    List<ExpenseSplit> findAllByUserIdAndExpenseIds(Long userId, List<Long> expenseIds);
}
