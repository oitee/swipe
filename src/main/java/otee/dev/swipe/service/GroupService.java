package otee.dev.swipe.service;

import org.springframework.stereotype.Service;
import otee.dev.swipe.dto.GroupDtos;
import otee.dev.swipe.model.*;
import otee.dev.swipe.util.ServiceResponse;

import java.util.Map;
import java.util.Optional;

@Service
public class GroupService {
    GroupRepository groupRepository;
    UserRepository userRepository;
    GroupMemberRepository groupMembersRepository;
    public GroupService(GroupRepository groupRepository, UserRepository userRepository, GroupMemberRepository groupMembersRepository){
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.groupMembersRepository = groupMembersRepository;
    }

    public GroupDtos.AddGroupDto addGroup(String name, String username, String description){
        if(groupRepository.findByName(name).isPresent()){
            return new GroupDtos.AddGroupDto(false, "Group Name already exists");
        }
        if(ServiceResponse.isNullOrBlank(username)){
            return new GroupDtos.AddGroupDto(false, "Username is empty");
        }
        Optional<User> createdBy = userRepository.findByUsername(username);
        if(createdBy.isEmpty()){
            return new GroupDtos.AddGroupDto(false, "User does not exist");
        }
        Group group = new Group(name, createdBy.get().getId());
        if(!ServiceResponse.isNullOrBlank(description)){
            group.setDescription(description);
        }
        Group res = groupRepository.save(group);
        GroupMember member = new GroupMember(res.getId(), createdBy.get().getId());
        groupMembersRepository.save(member);
        return new GroupDtos.AddGroupDto(res.getId(), createdBy.get().getUsername());
    }

    public GroupDtos.AddGroupMemberDto addGroupMember(Long groupId, String username){
        Optional<Group> group = groupRepository.findById(groupId);
        Optional<User> user = userRepository.findByUsername(username);
        if(group.isEmpty()){
            return new GroupDtos.AddGroupMemberDto(false, "Group does not exist");
        }
        if(user.isEmpty()){
            return new GroupDtos.AddGroupMemberDto(false, "User does not exist");
        }
        if(groupMembersRepository.findByUserIdAndGroupId(user.get().getId(), group.get().getId()).isPresent()){
            return new GroupDtos.AddGroupMemberDto(false, "User is already part of the Group");
        }
        GroupMember groupMember = new GroupMember(group.get().getId(), user.get().getId());
        groupMembersRepository.save(groupMember);
        return new GroupDtos.AddGroupMemberDto(group.get().getId(), group.get().getName(), user.get().getUsername());
    }
}
