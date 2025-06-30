package otee.dev.swipe.service;

import org.springframework.stereotype.Service;
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

    public Map<String, String> addGroup(String name, String username, String description){
        if(groupRepository.findByName(name).isPresent()){
            return ServiceResponse.defaultResponse(true, "Group Name already exists");
        }
        if(ServiceResponse.isNullOrBlank(username)){
            return ServiceResponse.defaultResponse(true, "Username is empty");
        }
        Optional<User> createdBy = userRepository.findByUsername(username);
        if(createdBy.isEmpty()){
            return ServiceResponse.defaultResponse(true, "User does not exist");
        }
        Group group = new Group(name, createdBy.get().getId());
        if(!ServiceResponse.isNullOrBlank(description)){
            group.setDescription(description);
        }
        Group res = groupRepository.save(group);
        GroupMember member = new GroupMember(res.getId(), createdBy.get().getId());
        groupMembersRepository.save(member);
        return ServiceResponse.defaultResponse(false, "Added new group! Group id: " + res.getId());
    }

    public Map<String, String> addGroupMember(Long groupId, String username){
        Optional<Group> group = groupRepository.findById(groupId);
        Optional<User> user = userRepository.findByUsername(username);
        if(group.isEmpty()){
            return ServiceResponse.defaultResponse(true, "Group does not exist");
        }
        if(user.isEmpty()){
            return ServiceResponse.defaultResponse(true, "User does not exist");
        }
        if(groupMembersRepository.findByUserIdAndGroupId(user.get().getId(), group.get().getId()).isPresent()){
            return ServiceResponse.defaultResponse(true, "User is already part of the Group");
        }
        GroupMember groupMember = new GroupMember(group.get().getId(), user.get().getId());
        groupMembersRepository.save(groupMember);
        return ServiceResponse.defaultResponse(false, "Group Member saved to Group: " + group.get().getName());
    }
}
