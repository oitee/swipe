package otee.dev.swipe;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class swipeController {
    @GetMapping("/")
    public String helloWorld(){
        return "Hello!";
    }
}
