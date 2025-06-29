package otee.dev.swipe.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import otee.dev.swipe.api.AddGroupRequest;
import otee.dev.swipe.model.Group;
import otee.dev.swipe.service.GroupService;

import java.util.Optional;

@Controller
public class GroupController {
    GroupService groupService;
    public GroupController(GroupService groupService){
        this.groupService = groupService;
    }

    @PostMapping("/addgroup/")
    public ResponseEntity<String> addGroup(@RequestBody AddGroupRequest addGroupRequest){
        if(addGroupRequest.getName().isBlank()){
            return new ResponseEntity<String>("Group name cannot be blank", HttpStatus.BAD_REQUEST);
        }
        if (addGroupRequest.getUsername().isBlank()){
            return new ResponseEntity<String>("User name cannot be blank", HttpStatus.BAD_REQUEST);
        }
        Group res = groupService.addGroup(addGroupRequest.getName(), addGroupRequest.getUsername());
        if (res == null){
            return new ResponseEntity<String>("Could not add group. Name exists already.", HttpStatus.BAD_REQUEST);
        }
        String response = "Add new group! Group id: " + res.getId();
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }
}
