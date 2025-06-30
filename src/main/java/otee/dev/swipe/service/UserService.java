package otee.dev.swipe.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import otee.dev.swipe.api.SignUpRequest;
import otee.dev.swipe.model.User;
import otee.dev.swipe.model.UserRepository;
import otee.dev.swipe.security.PasswordConfig;
import otee.dev.swipe.util.ServiceResponse;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Map<String, String> signUp(String username, String email, String password){
        if(userRepository.existsByUsernameOrEmail(username, email))
        {
            return ServiceResponse.defaultResponse(true, "Username already exists!");
        }
        User user = new User(username, email, passwordEncoder.encode(password));
        userRepository.save(user);
        System.out.println("ADDED NEW USER!");
        return ServiceResponse.defaultResponse(false, "Successful signup!");
    }

    public Map<String, String> signIn(String username, String password){
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()){
            System.out.println("NO USER FOUND BY THAT NAME");
            return ServiceResponse.defaultResponse(true,"Username not found");
        }
        User user = optionalUser.get();
        if(!passwordEncoder.matches(password,user.getPassword())){
            System.out.println("USER FOUND BUT PASSWORD DOES NOT MATCH");
            return ServiceResponse.defaultResponse(true, "Incorrect Password");
        }
        System.out.println("USER FOUND AND MATCHES PASSWORD");
        return ServiceResponse.defaultResponse(false, "Welcome to Swipe!");
    }

}
