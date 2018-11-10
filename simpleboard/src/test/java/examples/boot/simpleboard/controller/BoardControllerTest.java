package examples.boot.simpleboard.controller;

import examples.boot.simpleboard.domain.Board;
import examples.boot.simpleboard.domain.User;
import examples.boot.simpleboard.service.BoardService;
import examples.boot.simpleboard.service.MyService;
import examples.boot.simpleboard.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// 1. RunWith는 Runner를 구현한 객체를 이용해서 Test를 수행한다.
// 2. SpringRunner는 내부적으로 ApplciationContext를 가지고 있고, 해당 Test객체도 Bean으로써 등록된다.
// 3. @WebMvcTest : Spring MVC를 테스트를 사용하기 위한 애노테이션이다.
//    Controller는 여러개의 Service를 사용한다. Service의 구현과 상관없이 Controller를 테스트해야한다.
//    이것이 올바른 Controller의 단위테스트가 된다.
//    @WebMvcTest(BoardController.class) : BoardController를 테스트하겠다.
@RunWith(SpringRunner.class)
@WebMvcTest(BoardController.class)
public class BoardControllerTest {
    // 4. @WebMvcTest를 사용하면 MockMvc를 주입받을 수 있다. WAS를 실행하지 않고, Controller를 실행하도록 도와준다.
    @Autowired
    private MockMvc mvc;

    // BoardController가 의존하고 있는 Service를 Mock으로 설정한다.
    @MockBean
    BoardService boardService;
    @MockBean
    UserService userService;
    @MockBean
    MyService myService;

    @Test
    @WithMockUser(username = "urstory", authorities = {"USER"})
    public void testPostBoard() throws Exception{

    }

    // Test메소드를 만든다.
    // 5. 테스트를 할 때 Spring security와 관련된 부분을 도와주는 라이브러리르 추가한다.
//            <dependency>
//            <groupId>org.springframework.security</groupId>
//            <artifactId>spring-security-test</artifactId>
//            <version>5.0.4.RELEASE</version>
//            <scope>test</scope>
//        </dependency>
    //    @WithMockUser : 로그인한 사용자의 username과 권한을 지정할 수 있다.
    @Test
    @WithMockUser(username = "urstory@gmail.com", authorities = {"USER"})
    public void testGetBoards() throws Exception {
        List<Board> list = new ArrayList<>();
        list.add(createBoard(1L, "test1", "content1"));
        list.add(createBoard(2L, "test2", "content2"));
        list.add(createBoard(3L, "test3", "content3"));
        list.add(createBoard(4L, "test4", "content4"));

        Pageable pageable = PageRequest.of(1, 10);
        Page<Board> page = new PageImpl<>(list, pageable, 4);

        // Mock객체인 boardService의 getBoardList메소드가 호출되면
        // page객체가 리턴되도록 한다.
        given(boardService.getBoardList(1, null, null)).willReturn(page);

        // 6. mvc는 Spring MVC가 controller를 실행하는 것을 시뮬레이션 한다.
        //    perform메소드는 controller의 특정메소드를 get, post, put, delete등으로 실행하도록 한다.
        mvc.perform(get("/boards").with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest mockHttpServletRequest) {
                User user = new User();
                user.setId(1L);
                user.setName("홍길동");
                mockHttpServletRequest.setAttribute("loginUser", user);
                return mockHttpServletRequest;
            }
        }))
        .andExpect(status().isOk()) // 200 OK면 성공
        .andExpect(model().attribute("list", page)) // ModelMap에 값을 담으면, 템플릿까지 값이 전달된다. Controller에서 ModelMap에 "list"란 이름으로 값을 담았는데, 그게 page와 같은지 본다.
        .andExpect(content().string(containsString("test1"))); // content()는 템플릿엔진을 통해 랜더링한 결과에 "test1"이라는 문자열이 있는지 본다.
    }

    @Test
    @WithMockUser(username = "urstory", authorities = {"USER"})
    public void testGetBoard() throws Exception {
        Board board = createBoard(1L, "test1", "content1");

        given(boardService.getBoard(1L)).willReturn(board);

        // with()부분은 Controller가 아닌 인터셉터에서 로그인한 정보를 저장하는데,
        // 이 부분이 템플릿에서 사용하기 때문에 request에 로그인한 정보를 넣어줌.
        ResultActions content1 = mvc.perform(get("/boards/1")
                .with(mockHttpServletRequest -> {
                    User user = new User();
                    user.setId(1L);
                    user.setName("홍길동");
                    mockHttpServletRequest.setAttribute("loginUser", user);
                    return mockHttpServletRequest;
                }))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("content1")));
    }

    private Board createBoard(Long id, String title, String content){
        User user = new User();
        user.setId(1L);
        user.setName("홍길동");
        user.setEmail("hong@example.com");
        user.setPassword("");
        user.setRegdate(LocalDateTime.now());
        Board board = new Board();
        board.setId(id);
        board.setTitle(title);
        board.setContent(content);
        board.setUser(user);
        board.setReadCount(0);
        board.setRegdate(LocalDateTime.now());
        return board;
    }
}
