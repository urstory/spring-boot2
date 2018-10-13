package examples.boot.myshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/boards")
public class BoardController {
    //  GET /boartds
    @GetMapping
    public String list(){

        return "list"; // view name 을 리턴한다.
    }
}
