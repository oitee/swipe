package otee.dev.swipe.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import otee.dev.swipe.dto.UserDto;
import otee.dev.swipe.model.User;
import otee.dev.swipe.model.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto signUp(String username, String email, String password){
        if(userRepository.existsByUsernameOrEmail(username, email))
        {
            return new UserDto(false, "Username already exists!");
        }
        User user = new User(username, email, passwordEncoder.encode(password));
        User res = userRepository.save(user);
        return new UserDto(res.getId(), res.getUsername(), "Successful Signup!");
    }

    public UserDto signIn(String username, String password){
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()){
            return new UserDto(false, "Username not found");
        }
        User user = optionalUser.get();
        if(!passwordEncoder.matches(password,user.getPassword())){
            return new UserDto(false, "Incorrect Password");
        }
        return new UserDto(user.getId(), user.getUsername(), "Welcome to Swipe!");
    }
}
