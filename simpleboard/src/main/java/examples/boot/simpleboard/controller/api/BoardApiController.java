package examples.boot.simpleboard.controller.api;

import examples.boot.simpleboard.domain.Board;
import examples.boot.simpleboard.domain.User;
import examples.boot.simpleboard.security.LoginUserInfo;
import examples.boot.simpleboard.service.BoardService;
import examples.boot.simpleboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/boards")
public class BoardApiController {
    @Autowired
    BoardService boardService;

    @Autowired
    UserService userService;

    @GetMapping
    public String boardList(){
        return "boards/list";
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<Board> boardDetail(@PathVariable(name = "id") Long id){
        Board board = boardService.getBoard(id);
        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @PostMapping
    public String boardWrite(LoginUserInfo loginUserInfo, @RequestBody Board board){
        User user = userService.getUserByEmail(loginUserInfo.getEmail());
        board.setUser(user);
        boardService.addBoard(board);
        return "redirect:/boards";
    }

    @PutMapping
    public String boardUpdate(Principal principal, @RequestBody Board board){

        LoginUserInfo loginUserInfo = (LoginUserInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Board alreadyBoard = boardService.getBoard(board.getId());
        if(!alreadyBoard.getUser().getId().equals(loginUserInfo.getId())){ // 로그인 한 사용자와 해당 게시물의 소유자가 같지 않다면.
            throw new IllegalArgumentException("게시물의 작성자가 아닙니다.");
        }

        alreadyBoard.setTitle(board.getTitle());
        alreadyBoard.setContent(board.getContent());

        boardService.updateBoard(alreadyBoard);
        return "redirect:/boards";
    }
}
