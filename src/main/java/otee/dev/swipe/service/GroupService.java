package otee.dev.swipe.service;

import org.springframework.stereotype.Service;
import otee.dev.swipe.model.Group;
import otee.dev.swipe.model.GroupRepository;
import otee.dev.swipe.model.User;
import otee.dev.swipe.model.UserRepository;
import otee.dev.swipe.util.ServiceResponse;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Service
public class GroupService {
    GroupRepository groupRepository;
    UserRepository userRepository;
    public GroupService(GroupRepository groupRepository, UserRepository userRepository){
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public Group addGroup(String name, String username){
        if(groupRepository.findByName(name).isPresent()){
            return null;
        }
        String[] users = new String[1];
        users[0] = username;
        Group group = new Group(name, users);
        return groupRepository.save(group);
    }

    public Map<String, String> addGroupMember(Long groupId, String username){
        Optional<Group> group = groupRepository.findById(groupId);
        Optional<User> user = userRepository.findByUsername(username);
        if(group.isEmpty()){
            return ServiceResponse.addGroupResponse(true, "Group does not exist");
        }
        if(user.isEmpty()){
            return ServiceResponse.addGroupResponse(true, "User does not exist");
        }
        String[] existingUsers = group.get().getUsers();
        boolean found = Arrays.asList(existingUsers).contains(username);
        if(found){
            return ServiceResponse.addGroupResponse(true, "User is already part of the group");
        }
        String[] newUsers = Arrays.copyOf(existingUsers, existingUsers.length + 1);
        newUsers[newUsers.length - 1] = username;
        groupRepository.updateUsers(newUsers, group.get().getId());
        return ServiceResponse.addGroupResponse(false, "Added user " + user.get().getUsername() + " to group: " + group.get().getName());
    }
}
