package otee.dev.swipe.model;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GroupRepository extends CrudRepository<Group, Long> {
    @Query("UPDATE groups SET users = :users, updated_at = CURRENT_TIMESTAMP WHERE id = :id RETURNING *")
    Group updateUsers(@Param("users") String[] users, @Param("id") Long id);

    Optional<Group> findByName(String name);
}
