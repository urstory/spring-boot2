package examples.boot.simpleboard;

import examples.boot.simpleboard.domain.Board;
import examples.boot.simpleboard.domain.User;
import examples.boot.simpleboard.repository.BoardRepository;
import examples.boot.simpleboard.repository.UserRepository;
import examples.boot.simpleboard.service.BoardService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BoardRepositoryTest {
    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;

    @Before
    public void init(){
        User user = new User();
        user.setEmail("urstory@gmail.com");
        user.setName("김성박");
        userRepository.save(user);
    }

    @Test
    public void testDsl() throws Exception{
        User user = userRepository.findUserByEmail("urstory@gmail.com");
        Board board = new Board();
        board.setUser(user);
        board.setTitle("test");
        board.setContent("hello world");
        boardRepository.save(board);

        Board boardByDSL = boardRepository.getBoardByDSL(board.getId());

        System.out.println("----------------------------");
        System.out.println(boardByDSL.getId());
        System.out.println(boardByDSL.getTitle());
        System.out.println("----------------------------");

//        Board board2 = boardRepository.getBoardFetchJoinByDSL(1L);
//        Assert.assertNotNull(board2);
//
//        Assert.assertEquals("test", board2.getTitle());
//        Assert.assertEquals("김성박", board2.getUser().getName());
//
//        List<Board> list1 = boardRepository.getBoards("es");
//        Assert.assertEquals(1, list1.size());
//
//        List<Board> list2 = boardRepository.getBoards(null);
//        Assert.assertEquals(1, list2.size());
    }

    @Test
    public void testBoardCount() throws Exception{
        User user = userRepository.findUserByEmail("urstory@gmail.com");
        Board board = new Board();
        board.setUser(user);
        board.setTitle("test");
        board.setContent("hello world");
        boardRepository.save(board);

        long count = boardRepository.countByTitleContains("test");
        Assert.assertEquals(1L, count);

        count = boardRepository.countByContentContains("llo");
        Assert.assertEquals(1L, count);

    }

    @Test
    public void testFindBoardBy() throws Exception{
        User user = userRepository.findUserByEmail("urstory@gmail.com");
        Board board = new Board();
        board.setUser(user);
        board.setTitle("test");
        board.setContent("hello world");
        boardRepository.save(board);

        Board board2 = new Board();
        board2.setUser(user);
        board2.setTitle("test2");
        board2.setContent("hello2 world2");
        boardRepository.save(board2);

        Board board3 = new Board();
        board3.setUser(user);
        board3.setTitle("test3");
        board3.setContent("hello3 world2");
        boardRepository.save(board3);

        PageRequest pageRequest = PageRequest.of(0, 2,new Sort(Sort.Direction.DESC, "regdate"));
        Page<Board> list = boardRepository.findAllBy(pageRequest);
        for(Board b : list){
            System.out.println(b.getTitle());
            System.out.println(b.getRegdate());
            System.out.println(b.getUser().getName());
            System.out.println("--------------------------------------------------");
        }

        pageRequest = PageRequest.of(1, 2,new Sort(Sort.Direction.DESC, "regdate"));
        list = boardRepository.findAllBy(pageRequest);
        for(Board b : list){
            System.out.println(b.getTitle());
            System.out.println(b.getRegdate());
            System.out.println(b.getUser().getName());
            System.out.println("==================================================");
        }
    }
}
