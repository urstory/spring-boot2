package examples.boot.simpleboard.controller;

import examples.boot.simpleboard.PagerModel;
import examples.boot.simpleboard.domain.Board;
import examples.boot.simpleboard.domain.User;
import examples.boot.simpleboard.security.LoginUserInfo;
import examples.boot.simpleboard.service.BoardService;
import examples.boot.simpleboard.service.MyService;
import examples.boot.simpleboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/boards")
public class BoardController {

    @Value("${my.address}")
    private String address;

    @Autowired
    BoardService boardService;

    @Autowired
    UserService userService;

    @Autowired
    MyService myService;

    @GetMapping
    public String boardList(@RequestParam(name = "page", defaultValue = "1") int page,
                            @RequestParam(name = "searchType", required = false) String searchType,
                            @RequestParam(name = "searchStr", required = false) String searchStr,
                            ModelMap modelMap){
        Page<Board> boardPage = boardService.getBoardList(page, searchType, searchStr);

        PagerModel pagerModel = new PagerModel();
        pagerModel.setButtonCount(5); // page의 수
        pagerModel.setSearchStr(searchStr);
        pagerModel.setSearchType(searchType);
        pagerModel.setThisPage(page);
        pagerModel.setTotalPage(boardPage.getTotalPages());
        pagerModel.setTotalCount(boardPage.getTotalElements());
        if(pagerModel.getTotalPage() == 0)
            pagerModel.setTotalPage(1);

        modelMap.addAttribute("list", boardPage);
        modelMap.addAttribute("pager", pagerModel);

        return "boards/list";
    }

    @GetMapping(path="/{id}")
    public String boardDetail(@PathVariable(name = "id") Long id, ModelMap modelMap){
        Board board = boardService.getBoard(id);

        modelMap.addAttribute("board", board);

        // http://www.baeldung.com/thymeleaf-in-spring-mvc
        // http://cyberx.tistory.com/132
        return "boards/boardDetail";
    }

    @GetMapping(path="/writeform")
    public String boardWriteform(Principal principal, ModelMap modelMap){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userService.getUserByEmail(principal.getName());
        modelMap.addAttribute(user);

        return "boards/writeform";
    }

    @PostMapping
    public String boardWrite(LoginUserInfo loginUserInfo, @ModelAttribute Board board){
        User user = userService.getUserByEmail(loginUserInfo.getEmail());
        board.setUser(user);
        boardService.addBoard(board);
        return "redirect:/boards";
    }

    @DeleteMapping
    @ResponseBody
    public String boardDelete(){
        return "{\"success\":\"ok\"}";
    }

    @GetMapping(path="/updateform")
    public String boardUpdateform(
            @RequestParam(name = "id", required = true) long boardId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "searchType", required = false) String searchType,
            @RequestParam(name = "searchStr", required = false) String searchStr,
            ModelMap modelMap
    ){
        LoginUserInfo loginUserInfo = (LoginUserInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Board board = boardService.getBoard(boardId);
        if(!board.getUser().getId().equals(loginUserInfo.getId())){ // 로그인 한 사용자와 해당 게시물의 소유자가 같지 않다면.
            throw new IllegalArgumentException("게시물의 작성자가 아닙니다.");
        }
        modelMap.addAttribute("board", board);
        return "boards/updateform";
    }

    @PutMapping
    public String boardUpdate(){
        return "redirect:/boards";
    }

    @GetMapping(path="/deleteform")
    public String boardDeleteForm(
            @RequestParam(name = "id", required = true) long boardId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "searchType", required = false) String searchType,
            @RequestParam(name = "searchStr", required = false) String searchStr,
            ModelMap modelMap){

        LoginUserInfo loginUserInfo = (LoginUserInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Board board = boardService.getBoard(boardId);
        if(!board.getUser().getId().equals(loginUserInfo.getId())){ // 로그인 한 사용자와 해당 게시물의 소유자가 같지 않다면.
            throw new IllegalArgumentException("게시물의 작성자가 아닙니다.");
        }
        modelMap.addAttribute("board", board);
        return "boards/deleteform";
    }

    @GetMapping(path="/delete")
    public String delete(
            @RequestParam(name = "id", required = true) long boardId){

        LoginUserInfo loginUserInfo = (LoginUserInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Board board = boardService.getBoard(boardId);
        if(!board.getUser().getId().equals(loginUserInfo.getId())){ // 로그인 한 사용자와 해당 게시물의 소유자가 같지 않다면.
            throw new IllegalArgumentException("게시물의 작성자가 아닙니다.");
        }
        boardService.deleteBaord(board.getId());
        return "redirect:/boards";
    }
}
