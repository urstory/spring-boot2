package examples.boot.myshop.controller;

import examples.boot.myshop.security.AuthUser;
import examples.boot.myshop.security.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// http://localhost:8080/boards?page=2
@Controller
@RequestMapping("/boards")
public class BoardController {

    //  GET /boards - 1page 의 결과를 달라.
    //  GET /boards?page=2   - 2page의 결과를 달라.
    //  GET /boards?page=3   - 3page의 결과를 달라.
    //  파라미터 searchKind
    //  파라미터 searchStr  2개의 파라미터는 반드시 필요한 것은 아니다. String type
    @GetMapping
    public String list(
            @AuthUser LoginUser loginUser,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "searchKind", required = false) String searchKind,
            @RequestParam(name = "searchStr", required = false) String searchStr
    ){
//
//        LoginUser loginUser = (LoginUser)SecurityContextHolder
//                .getContext().getAuthentication().getPrincipal();
        if(loginUser != null){
            System.out.println("----------------------");
            System.out.println(loginUser.getId());
            System.out.println(loginUser.getName());
            System.out.println(loginUser.getEmail());
            System.out.println("----------------------");
        }
        System.out.println("page : " + page);
        System.out.println("searchKind : " + searchKind);
        System.out.println("searchStr : " + searchStr);
        return "list"; // view name 을 리턴한다.
    }

    @GetMapping(path = "/{boardId}")
    public String read(
        @PathVariable(name = "boardId") Long boardId,
        @RequestParam(name = "page", required = false, defaultValue = "1") int page,
        @RequestParam(name = "searchKind", required = false) String searchKind,
        @RequestParam(name = "searchStr", required = false) String searchStr
    ){
        System.out.println("boardId :" + boardId);
        return "view"; // view name을 리턴한다. view.jsp 를 만들어줘야한다.
    }

    @GetMapping(path = "/write")
    public String writeform(){
        return "writeform"; // 이름, 제목, 내용을 입력하는 폼을 작성해주세요.
    }

    @PostMapping
    public String write(
            @RequestParam(name = "name", required = true) String name,
            @RequestParam(name = "title", required = true) String title,
            @RequestParam(name = "content", required = true) String content
    ){
        System.out.println("name : " + name);
        System.out.println("title : " + title);
        System.out.println("content : " + content);
        return "redirect:/boards"; // write jsp 를 보여준다.
    }
}
