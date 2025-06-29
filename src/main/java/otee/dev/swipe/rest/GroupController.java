package otee.dev.swipe.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import otee.dev.swipe.api.AddGroupMember;
import otee.dev.swipe.api.AddGroupRequest;
import otee.dev.swipe.service.GroupService;
import otee.dev.swipe.util.ServiceResponse;

import java.util.Map;

@Controller
public class GroupController {
    GroupService groupService;
    public GroupController(GroupService groupService){
        this.groupService = groupService;
    }

    @PostMapping("/add-group/")
    public ResponseEntity<String> addGroup(@RequestBody AddGroupRequest addGroupRequest){
        if(ServiceResponse.isNullOrBlank(addGroupRequest.getName())){
            return new ResponseEntity<String>("Group name cannot be blank", HttpStatus.BAD_REQUEST);
        }
        if (ServiceResponse.isNullOrBlank(addGroupRequest.getUsername())){
            return new ResponseEntity<String>("User name cannot be blank", HttpStatus.BAD_REQUEST);
        }
        Map<String, String> res = groupService.addGroup(addGroupRequest.getName(), addGroupRequest.getUsername());
        HttpStatus status;
        if (Boolean.parseBoolean(res.get("isError"))){
            status = HttpStatus.BAD_REQUEST;
        }
        else{
            status = HttpStatus.OK;
        }
        return new ResponseEntity<String>(res.get("message"), status);
    }

    @PostMapping("/add-member/")
    public ResponseEntity<String> addGroupMember(@RequestBody AddGroupMember addGroupMember){
        if(ServiceResponse.isNullOrBlank(addGroupMember.getGroupId())){
            return new ResponseEntity<String>("Group ID missing", HttpStatus.BAD_GATEWAY);
        }
        if(ServiceResponse.isNullOrBlank(addGroupMember.getUsername())){
            return new ResponseEntity<String>("User name missing", HttpStatus.BAD_REQUEST);
        }
        Map<String, String> response = groupService.addGroupMember(addGroupMember.getGroupId(), addGroupMember.getUsername());
        HttpStatus status;
        if(Boolean.parseBoolean(response.get("isError"))){
            status = HttpStatus.BAD_REQUEST;
        } else{
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(response.get("message"), status);
    }
}