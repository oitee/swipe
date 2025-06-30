package otee.dev.swipe.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Table("group_members")
public class GroupMember {
    @Id
    private Long id;
    private Long groupId;
    private Long userId;
    private Boolean isActive;
    private OffsetDateTime joinedAt;

    public GroupMember(){}
    public GroupMember(Long groupId, Long userId){
        this.groupId = groupId;
        this.userId = userId;
        this.isActive = true;
        this.joinedAt = OffsetDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public OffsetDateTime getJoinedAt() {
        return joinedAt;
    }
}
