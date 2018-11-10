package examples.boot.simpleboard.controller.api;

import examples.boot.simpleboard.dto.Test;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestApiController {
    @PostMapping
    public Test postTest(@RequestBody Test test){
        System.out.println(test);
        test.setTitle(test.getTitle() + "!!!!!!!!!!!!!!!");
        return test;
    }

    @PutMapping
    public Test postTest(@RequestParam(name = "title")String title){
        System.out.println("title : " + title);
        Test test = new Test();
        test.setTitle(title);
        return test;
    }
}
