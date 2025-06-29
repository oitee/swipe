package otee.dev.swipe.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import otee.dev.swipe.api.SignInRequest;
import otee.dev.swipe.api.SignUpRequest;
import otee.dev.swipe.service.UserService;
import otee.dev.swipe.util.ServiceResponse;
import java.util.Map;

@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signup/")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUpRequest){
        if(ServiceResponse.isNullOrBlank(signUpRequest.getUsername())){
            return new ResponseEntity<>("Username empty", HttpStatus.BAD_REQUEST);
        }
        if(ServiceResponse.isNullOrBlank(signUpRequest.getEmail())){
            return new ResponseEntity<>("Email empty", HttpStatus.BAD_REQUEST);
        }
        if(ServiceResponse.isNullOrBlank(signUpRequest.getPassword())){
            return new ResponseEntity<>("Password empty", HttpStatus.BAD_REQUEST);
        }
        Map<String, String> res = userService.signUp(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());
        HttpStatus status;
        if(Boolean.parseBoolean(res.get("isError"))){
            status = HttpStatus.BAD_REQUEST;
        }
        else{
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(res.get("message"), status);
    }

    @PostMapping("/signin/")
    public ResponseEntity<String> signIn(@RequestBody SignInRequest signInRequest){
        if(ServiceResponse.isNullOrBlank(signInRequest.getPassword())){
            return new ResponseEntity<>("Password empty", HttpStatus.BAD_REQUEST);
        }
        if(ServiceResponse.isNullOrBlank(signInRequest.getUsername())){
            return new ResponseEntity<>("Username empty", HttpStatus.BAD_REQUEST);
        }
        Map<String, String> res = userService.signIn(signInRequest.getUsername(), signInRequest.getPassword());
        HttpStatus status;
        if(Boolean.parseBoolean(res.get("isError"))){
            status = HttpStatus.BAD_REQUEST;
        } else{
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(res.get("message"), status);
    }

}
