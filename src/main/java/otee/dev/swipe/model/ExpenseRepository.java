package otee.dev.swipe.model;

import org.springframework.data.repository.CrudRepository;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {
}
