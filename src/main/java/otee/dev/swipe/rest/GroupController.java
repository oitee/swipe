package otee.dev.swipe.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import otee.dev.swipe.api.AddGroupMember;
import otee.dev.swipe.api.AddGroupRequest;
import otee.dev.swipe.dto.GroupDtos;
import otee.dev.swipe.service.GroupService;
import otee.dev.swipe.util.ServiceResponse;

import java.util.Map;

@RestController
public class GroupController {
    GroupService groupService;
    public GroupController(GroupService groupService){
        this.groupService = groupService;
    }

    @PostMapping("/add-group/")
    public ResponseEntity<GroupDtos.AddGroupDto> addGroup(@RequestBody AddGroupRequest addGroupRequest){
        if(ServiceResponse.isNullOrBlank(addGroupRequest.getName())){
            GroupDtos.AddGroupDto badResponse = new GroupDtos.AddGroupDto(false, "Group name cannot be blank");
            return new ResponseEntity<GroupDtos.AddGroupDto>(badResponse, HttpStatus.BAD_REQUEST);
        }
        if (ServiceResponse.isNullOrBlank(addGroupRequest.getUsername())){
            GroupDtos.AddGroupDto badResponse = new GroupDtos.AddGroupDto(false, "Username cannot be blank");
            return new ResponseEntity<GroupDtos.AddGroupDto>(badResponse, HttpStatus.BAD_REQUEST);
        }
        GroupDtos.AddGroupDto res = groupService.addGroup(addGroupRequest.getName(), addGroupRequest.getUsername(), addGroupRequest.getDescription());
        HttpStatus status;
        if (res.getSuccess()){
            status = HttpStatus.OK;
        }
        else{
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<GroupDtos.AddGroupDto>(res, status);
    }

    @PostMapping("/add-member/")
    public ResponseEntity<GroupDtos.AddGroupMemberDto> addGroupMember(@RequestBody AddGroupMember addGroupMember){
        if(ServiceResponse.isNullOrBlank(addGroupMember.getGroupId())){
            GroupDtos.AddGroupMemberDto badResponse = new GroupDtos.AddGroupMemberDto(false, "Group ID missing");
            return new ResponseEntity<GroupDtos.AddGroupMemberDto>(badResponse, HttpStatus.BAD_GATEWAY);
        }
        if(ServiceResponse.isNullOrBlank(addGroupMember.getUsername())){
            GroupDtos.AddGroupMemberDto badResponse = new GroupDtos.AddGroupMemberDto(false, "Username missing");
            return new ResponseEntity<GroupDtos.AddGroupMemberDto>(badResponse, HttpStatus.BAD_REQUEST);
        }
        GroupDtos.AddGroupMemberDto response = groupService.addGroupMember(addGroupMember.getGroupId(), addGroupMember.getUsername());
        HttpStatus status;
        if(response.getSuccess()){
            status = HttpStatus.OK;
        } else{
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(response, status);
    }
}