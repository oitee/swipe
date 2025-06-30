package otee.dev.swipe.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Table("users")
public class User {
    @Id
    private Long id;
    private String username;
    private String email;
    private String password;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public User(){}

    public User(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
        this.updatedAt = OffsetDateTime.now();
        this.createdAt = OffsetDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}
