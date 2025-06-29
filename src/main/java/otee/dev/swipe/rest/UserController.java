package otee.dev.swipe.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import otee.dev.swipe.api.SignInRequest;
import otee.dev.swipe.api.SignUpRequest;
import otee.dev.swipe.model.User;
import otee.dev.swipe.service.UserService;

@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signup/")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUpRequest){
        User user = userService.signUp(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());
        if (user != null){
            return new ResponseEntity<>("Successful signup!!", HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<>("Sorry! Most likely, username is taken :(", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signin/")
    public ResponseEntity<String> signIn(@RequestBody SignInRequest signInRequest){
        String res = userService.signIn(signInRequest.getUsername(), signInRequest.getPassword());
        if (res != null){
            return new ResponseEntity<>("Welcome to Swipe!", HttpStatus.OK);
        }
        return new ResponseEntity<>("ERROR! Either incorrect password or username", HttpStatus.BAD_GATEWAY);
    }

}
