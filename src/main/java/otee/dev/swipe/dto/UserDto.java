package otee.dev.swipe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto{
    private Long userId;
    private String username;
    private Boolean success;
    private String message;

    public UserDto(Long userId, String username, String message){
        this.success = true;
        this.userId = userId;
        this.username = username;
        this.message = message;
    }
    public UserDto(Boolean success, String message){
        this.success = success;
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
