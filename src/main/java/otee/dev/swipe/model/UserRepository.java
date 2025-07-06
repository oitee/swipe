package otee.dev.swipe.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    // Spring Data JDBC automatically creates the query based on the method name
    Optional<User> findByUsername(String username);

    // Check if a user exists by username or email
    boolean existsByUsernameOrEmail(String username, String email);

    int removeById(long id);
}
