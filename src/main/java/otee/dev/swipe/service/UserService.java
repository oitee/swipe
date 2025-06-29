package otee.dev.swipe.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import otee.dev.swipe.api.SignUpRequest;
import otee.dev.swipe.model.User;
import otee.dev.swipe.model.UserRepository;
import otee.dev.swipe.security.PasswordConfig;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signUp(String username, String email, String password){
        if(userRepository.existsByUsernameOrEmail(username, email))
        {
            return null;
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setUsername(username);
        userRepository.save(user);
        System.out.println("ADDED NEW USER!");
        return user;
    }

    public String signIn(String username, String password){
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()){
            System.out.println("NO USER FOUND BY THAT NAME");
            return null;
        }
        User user = optionalUser.get();
        if(!passwordEncoder.matches(password,user.getPassword())){
            System.out.println("USER FOUND BUT PASSWORD DOES NOT MATCH");
            return null;
        }
        System.out.println("USER FOUND AND MATCHES PASSWORD");
        return user.getUsername();
    }

}
