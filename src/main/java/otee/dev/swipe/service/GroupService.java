package otee.dev.swipe.service;

import org.springframework.stereotype.Service;
import otee.dev.swipe.model.Group;
import otee.dev.swipe.model.GroupRepository;

import java.util.Optional;

@Service
public class GroupService {
    GroupRepository groupRepository;
    public GroupService(GroupRepository groupRepository){
        this.groupRepository = groupRepository;
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
}
