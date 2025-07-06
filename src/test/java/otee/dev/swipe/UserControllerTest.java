package otee.dev.swipe;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import com.google.gson.Gson;

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

    @Test
    public void testSignupFlow() throws  Exception{
        mockMvc.perform(get("/signup/")).andDo(print()).andExpect(status().is3xxRedirection());
        long salt = Math.round(Math.random() * 1000);
        String username = "AliceTest" + salt;
        String password = "WonderLand123456!";
        String email = "alice" + salt + "@wonder.com";

        Gson gson = new Gson();
        // Attempt sign up without username
        mockMvc.perform(post("/signup/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(new SignUpRequest(null, password, email))))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(gson.toJson(new UserDto(false, "Username empty"))));

        // Attempt sign up with empty password:
        mockMvc.perform(post("/signup/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(new SignUpRequest(username, null, email))))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(gson.toJson(new UserDto(false, "Password empty"))));

        // Attempt sign up with empty password:
        mockMvc.perform(post("/signup/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(new SignUpRequest(username, email, null))))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(gson.toJson(new UserDto(false, "Email empty"))));

        // Attempt sign up with correct params:
        mockMvc.perform(post("/signup/")
                        .content(gson.toJson(new SignUpRequest(username, password, email)))
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        // Retry sign-up with the same params:
        mockMvc.perform(post("/signup/")
                        .content(gson.toJson(new SignUpRequest(username, password, email)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(gson.toJson(new UserDto(false, "Username or email already exists!"))));

        // Cleaning up the user from the db:
        userService.removeUser(username, password);

    }
}
