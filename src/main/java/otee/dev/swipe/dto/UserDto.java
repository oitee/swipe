package otee.dev.swipe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

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
    public UserDto(String username){
        this.username = username;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UsersForGroupDto{
        private List<UserDto> users;
        private Boolean success;
        private  String message;

        public UsersForGroupDto(Boolean success, String message){
            this.success = success;
            this.message = message;
        }
        public UsersForGroupDto(List<UserDto> users){
            this.success = true;
            this.users = users;
        }

        public String getMessage() {
            return message;
        }

        public Boolean getSuccess() {
            return success;
        }

        public List<UserDto> getUsers() {
            return users;
        }
    }
}
