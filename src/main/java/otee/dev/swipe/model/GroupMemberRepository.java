package otee.dev.swipe.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GroupMemberRepository extends CrudRepository<GroupMember, Long> {
    Optional<GroupMember> findByUserIdAndGroupId(Long userId, Long groupId);
    List<GroupMember> findByGroupId(Long groupId);
}
