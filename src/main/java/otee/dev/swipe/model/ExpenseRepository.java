package otee.dev.swipe.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {
    List<Expense> findAllByGroupId(Long groupId);
}
