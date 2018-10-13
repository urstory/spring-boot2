package examples.boot.first;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloApiController {
    @GetMapping(path="/hello")
    public String hello(){
        return "hello spring boot";
    }
}
