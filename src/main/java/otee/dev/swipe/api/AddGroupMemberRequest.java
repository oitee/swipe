package otee.dev.swipe.api;

public class AddGroupMemberRequest {
    Long groupId;
    String username;

    public AddGroupMemberRequest(Long groupId, String username){
        this.groupId = groupId;
        this.username = username;
    }

    public Long getGroupId() {
        return groupId;
    }

    public String getUsername() {
        return username;
    }
}
