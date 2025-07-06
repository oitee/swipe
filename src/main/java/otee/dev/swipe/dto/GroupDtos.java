package otee.dev.swipe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class GroupDtos {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DefaultGroupDto {
        private Long groupId;
        private String groupName;
        private String description;
        private String addedBy;
        private String message;
        private Boolean success;
        public DefaultGroupDto(){}

        public DefaultGroupDto(Boolean success, String message){
            this.message = message;
            this.success= success;
        }
        public DefaultGroupDto(Long groupId, String addedBy){
            this.success = true;
            this.groupId = groupId;
            this.addedBy = addedBy;
        }
        public DefaultGroupDto(String groupName, Long groupId, String description){
            this.groupId = groupId;
            this.groupName = groupName;
            this.description = description;
        }

        public Long getGroupId() {
            return groupId;
        }

        public String getAddedBy() {
            return addedBy;
        }

        public String getMessage() {
            return message;
        }

        public Boolean getSuccess() {
            return success;
        }

        public String getGroupName() {
            return groupName;
        }

        public String getDescription() {
            return description;
        }
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AddGroupMemberDto{
        private Long groupId;
        private String groupName;
        private String username;
        private Boolean success;
        private String message;

        public AddGroupMemberDto(Boolean success, String message){
            this.success = success;
            this.message = message;
        }
        public AddGroupMemberDto(Long groupId, String groupName, String username){
            this.success = true;
            this.groupId = groupId;
            this.groupName = groupName;
            this.username = username;
        }

        public Long getGroupId() {
            return groupId;
        }

        public String getGroupName() {
            return groupName;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GroupMembersDto{
        private List<DefaultGroupDto> groups;
        private Boolean success;
        private String message;
        public GroupMembersDto(Boolean success, String message){
            this.success = success;
            this.message = message;
        }
        public GroupMembersDto(List<DefaultGroupDto> groups){
            this.success = true;
            this.groups = groups;
        }

        public List<DefaultGroupDto> getGroups() {
            return groups;
        }

        public Boolean getSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }
}
