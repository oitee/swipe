package otee.dev.swipe.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {
    // A "Bean" is just an object that Spring manages.
    // This bean is our password encoder.
    @Bean
    public PasswordEncoder passwordEncoder(){
        //choosing BCrypt as our hashing algorithm.
        return new BCryptPasswordEncoder();
    }
}
