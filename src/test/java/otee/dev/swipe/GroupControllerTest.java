package otee.dev.swipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import otee.dev.swipe.api.AddGroupRequest;
import otee.dev.swipe.api.SignUpRequest;
import otee.dev.swipe.dto.GroupDtos;
import otee.dev.swipe.service.GroupService;
import otee.dev.swipe.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class GroupControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    @Autowired
    GroupService groupService;

    public static String username = "AliceTest";
    public static String password = "WonderLand123456!";
    public static String email = "email@email.com";
    public static String groupName = "Test Group";
    public static String groupDescription = "Group for testing purposes";


    @Test
    void testAddGroup() throws Exception{

        AddGroupRequest addGroupRequest = new AddGroupRequest(groupName, username, groupDescription);
        // Attempt adding a new group with invalid user
        mockMvc.perform(post("/groups/add-group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addGroupRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("User does not exist")));

        // Add the user to the system:
        mockMvc.perform(post("/signup/")
                .content(objectMapper.writeValueAsString(new SignUpRequest(username, password, email)))
                .contentType(MediaType.APPLICATION_JSON));

        // Attempt to add a new group with empty username
        mockMvc.perform(post("/groups/add-group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new AddGroupRequest(groupName, null, groupDescription))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Username cannot be blank")));

        // Attempt to add a new group with empty group name
        mockMvc.perform(post("/groups/add-group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new AddGroupRequest(null, username, groupDescription))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Group name cannot be blank")));


        // Attempt adding a new group with correct params:
        String res = mockMvc.perform(post("/groups/add-group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addGroupRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.addedBy", is(username)))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.groupId", is(notNullValue())))
                .andReturn()
                .getResponse()
                .getContentAsString();
        long groupId = objectMapper.readValue(res, GroupDtos.DefaultGroupDto.class).getGroupId();
        // Attempt adding a new group with same name -> This should fail.
        mockMvc.perform(post("/groups/add-group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addGroupRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Group Name already exists")));

        // Clean up the user and group from the db:
        userService.removeUser(username, password);
        groupService.removeGroup(groupId);

    }
}
