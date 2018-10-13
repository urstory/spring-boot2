package examples.boot.myshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "searchKind", required = false) String searchKind,
            @RequestParam(name = "searchStr", required = false) String searchStr
    ){
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
}
