package otee.dev.swipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import otee.dev.swipe.api.SignInRequest;
import otee.dev.swipe.api.SignUpRequest;
import otee.dev.swipe.dto.UserDto;
import otee.dev.swipe.service.UserService;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testSignupFlow() throws  Exception{
        mockMvc.perform(get("/signup/")).andExpect(status().is3xxRedirection());
        long salt = Math.round(Math.random() * 1000);
        String username = "AliceTest" + salt;
        String password = "WonderLand123456!";
        String email = "alice" + salt + "@wonder.com";

        // Attempt sign up without username
        mockMvc.perform(post("/signup/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignUpRequest(null, password, email))))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(new UserDto(false, "Username empty"))));

        // Attempt sign up with empty password:
        mockMvc.perform(post("/signup/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignUpRequest(username, null, email))))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(new UserDto(false, "Password empty"))));

        // Attempt sign up with empty email:
        mockMvc.perform(post("/signup/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignUpRequest(username, email, null))))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(new UserDto(false, "Email empty"))));

        // Attempt sign up with correct params:
        mockMvc.perform(post("/signup/")
                        .content(objectMapper.writeValueAsString(new SignUpRequest(username, password, email)))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        // Retry sign-up with the same params:
        mockMvc.perform(post("/signup/")
                        .content(objectMapper.writeValueAsString(new SignUpRequest(username, password, email)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(new UserDto(false, "Username or email already exists!"))));

        // Cleaning up the user from the db:
        userService.removeUser(username, password);

    }

    @Test
    public void testSignInFlow() throws Exception{
        mockMvc.perform(get("/login/")).andExpect(status().is3xxRedirection());
        long salt = Math.round(Math.random() * 1000);
        String username = "AliceTest" + salt;
        String password = "WonderLand123456!";
        String email = "alice" + salt + "@wonder.com";
        // Signup the user:
        mockMvc.perform(post("/signup/")
                        .content(objectMapper.writeValueAsString(new SignUpRequest(username, password, email)))
                        .contentType(MediaType.APPLICATION_JSON));

        // Attempt sign in with empty password:
        mockMvc.perform(post("/signin/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignInRequest(username, null))))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(new UserDto(false, "Password empty"))));

        // Attempt sign in with incorrect password:
        mockMvc.perform(post("/signin/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignInRequest(username, "password"))))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(new UserDto(false, "Incorrect Password"))));

        // Attempt sign in with empty username:
        mockMvc.perform(post("/signin/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignInRequest(null, password))))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(new UserDto(false, "Username empty"))));

        // Attempt sign in with correct params
        mockMvc.perform(post("/signin/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignInRequest(username, password))))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new UserDto(true, "Welcome to Swipe!"))));

        // Re-attempt sign in with the same params:
        mockMvc.perform(post("/signin/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignInRequest(username, password))))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new UserDto(true, "Welcome to Swipe!"))));

        // Clean up the user from the db:
        userService.removeUser(username, password);
    }
}
