package otee.dev.swipe.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import otee.dev.swipe.api.AddGroupMember;
import otee.dev.swipe.api.AddGroupRequest;
import otee.dev.swipe.dto.GroupDtos;
import otee.dev.swipe.dto.TransactionDtos;
import otee.dev.swipe.dto.UserDto;
import otee.dev.swipe.service.GroupService;
import otee.dev.swipe.util.ServiceResponse;

@RestController
public class GroupController {
    GroupService groupService;
    public GroupController(GroupService groupService){
        this.groupService = groupService;
    }

    @PostMapping("/groups/add-group")
    public ResponseEntity<GroupDtos.DefaultGroupDto> addGroup(@RequestBody AddGroupRequest addGroupRequest){
        if(ServiceResponse.isNullOrBlank(addGroupRequest.getName())){
            GroupDtos.DefaultGroupDto badResponse = new GroupDtos.DefaultGroupDto(false, "Group name cannot be blank");
            return new ResponseEntity<GroupDtos.DefaultGroupDto>(badResponse, HttpStatus.BAD_REQUEST);
        }
        if (ServiceResponse.isNullOrBlank(addGroupRequest.getUsername())){
            GroupDtos.DefaultGroupDto badResponse = new GroupDtos.DefaultGroupDto(false, "Username cannot be blank");
            return new ResponseEntity<GroupDtos.DefaultGroupDto>(badResponse, HttpStatus.BAD_REQUEST);
        }
        GroupDtos.DefaultGroupDto res = groupService.addGroup(addGroupRequest.getName(), addGroupRequest.getUsername(), addGroupRequest.getDescription());
        HttpStatus status;
        if (res.getSuccess()){
            status = HttpStatus.OK;
        }
        else{
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<GroupDtos.DefaultGroupDto>(res, status);
    }

    @PostMapping("/groups/add-member")
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
    @GetMapping("/users/{username}/groups")
    public ResponseEntity<GroupDtos.GroupMembersDto> getGroupsForUser(@PathVariable String username){
        if(ServiceResponse.isNullOrBlank(username)){
            GroupDtos.GroupMembersDto badResponse = new GroupDtos.GroupMembersDto(false, "Username missing");
            return new ResponseEntity<>(badResponse, HttpStatus.BAD_REQUEST);
        }
        GroupDtos.GroupMembersDto response = groupService.getGroupsForUser(username);
        HttpStatus status;
        if(response.getSuccess()){
            status = HttpStatus.OK;
        } else{
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(response, status);
    }
    @GetMapping("/groups/{groupId}/users")
    public ResponseEntity<UserDto.UsersForGroupDto> getUsersForGroup(@PathVariable Long groupId){
        if(ServiceResponse.isNullOrBlank(groupId)){
            UserDto.UsersForGroupDto badResponse = new UserDto.UsersForGroupDto(false, "Group id is missing");
            return new ResponseEntity<>(badResponse, HttpStatus.BAD_REQUEST);
        }
        UserDto.UsersForGroupDto response = groupService.getUsersForGroup(groupId);
        HttpStatus status;
        if(response.getSuccess()){
            status = HttpStatus.OK;
        } else{
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/groups/{groupId}/expenses")
    public ResponseEntity<TransactionDtos.GroupExpensesWithUsernameDto> getExpensesForGroup(@PathVariable Long groupId){
        if(ServiceResponse.isNullOrBlank(groupId)){
            TransactionDtos.GroupExpensesWithUsernameDto badResponse = new TransactionDtos.GroupExpensesWithUsernameDto(false, "Group id is missing");
            return new ResponseEntity<>(badResponse, HttpStatus.BAD_REQUEST);
        }
        TransactionDtos.GroupExpensesWithUsernameDto response = groupService.getExpensesForGroup(groupId);
        HttpStatus status;
        if(response.getSuccess()){
            status = HttpStatus.OK;
        } else{
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(response, status);
    }
}