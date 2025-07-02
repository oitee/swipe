package otee.dev.swipe.model;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GroupMemberRepository extends CrudRepository<GroupMember, Long> {
    Optional<GroupMember> findByUserIdAndGroupId(Long userId, Long groupId);
    List<GroupMember> findByGroupId(Long groupId);
    @Query("""
            SELECT
            group_members.group_id,
            group_members.user_id,
            groups.name AS group_name,
            groups.description AS group_description
            FROM group_members
            INNER JOIN groups
            ON group_members.group_id = groups.id
            WHERE group_members.user_id = :userId
            """)
    List<GroupMemberWithGroup> findByUserIdWithGroupName(Long userId);
}
