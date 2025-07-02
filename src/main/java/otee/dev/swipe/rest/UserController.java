package otee.dev.swipe.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import otee.dev.swipe.api.SignInRequest;
import otee.dev.swipe.api.SignUpRequest;
import otee.dev.swipe.dto.UserDto;
import otee.dev.swipe.service.UserService;
import otee.dev.swipe.util.ServiceResponse;

@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signup/")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequest signUpRequest){
        if(ServiceResponse.isNullOrBlank(signUpRequest.getUsername())){
            UserDto badResponse = new UserDto(false, "Password empty");
            return new ResponseEntity<>(badResponse, HttpStatus.BAD_REQUEST);
        }
        if(ServiceResponse.isNullOrBlank(signUpRequest.getEmail())){
            UserDto badResponse = new UserDto(false, "Email empty");
            return new ResponseEntity<>(badResponse, HttpStatus.BAD_REQUEST);
        }
        if(ServiceResponse.isNullOrBlank(signUpRequest.getPassword())){
            UserDto badResponse = new UserDto(false, "Password empty");
            return new ResponseEntity<>(badResponse, HttpStatus.BAD_REQUEST);
        }
        UserDto res = userService.signUp(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());
        HttpStatus status;
        if(res.getSuccess()){
            status = HttpStatus.CREATED;
        }
        else{
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(res, status);
    }

    @PostMapping("/signin/")
    public ResponseEntity<UserDto> signIn(@RequestBody SignInRequest signInRequest){
        if(ServiceResponse.isNullOrBlank(signInRequest.getPassword())){
            UserDto badResponse = new UserDto(false, "Password empty");
            return new ResponseEntity<>(badResponse, HttpStatus.BAD_REQUEST);
        }
        if(ServiceResponse.isNullOrBlank(signInRequest.getUsername())){
            UserDto badResponse = new UserDto(false, "Username empty");
            return new ResponseEntity<>(badResponse, HttpStatus.BAD_REQUEST);
        }
        UserDto res = userService.signIn(signInRequest.getUsername(), signInRequest.getPassword());
        HttpStatus status;
        if(res.getSuccess()){
            status = HttpStatus.OK;
        } else{
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(res, status);
    }

}
