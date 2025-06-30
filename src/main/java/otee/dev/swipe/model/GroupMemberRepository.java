package otee.dev.swipe.model;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GroupMemberRepository extends CrudRepository<GroupMember, Long> {
    Optional<GroupMember> findByUserId(Long userId);
    Optional<GroupMember> findByUserIdAndGroupId(Long userId, Long groupId);
}
