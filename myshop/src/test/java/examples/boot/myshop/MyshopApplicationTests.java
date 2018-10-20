package examples.boot.myshop;

import examples.boot.myshop.entity.Board;
import examples.boot.myshop.entity.Category;
import examples.boot.myshop.repository.BoardRepository;
import examples.boot.myshop.repository.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // test에서 @Transactional을 사용하면 자동 롤백된다.
public class MyshopApplicationTests {
	@Autowired
	CategoryRepository categoryRepository; // test할 대상을 주입받는다.

	@Autowired
	BoardRepository boardRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void test1(){
		Category category = categoryRepository.getOne(1L);
		System.out.println(category.getId());
		System.out.println(category.getName());
		Category category2 = categoryRepository.getOne(1L);
		if(category == category2){
			System.out.println("category == category2");
		}
	}

	@Test
	public void test2(){
		System.out.println("---------------------------------");
		System.out.println(categoryRepository.getClass().getName());
		Category category = categoryRepository.getOne(1L);
		List<Board> boards = category.getBoards();
		System.out.println("---------------------------------");
		System.out.println(boards.getClass().getName());
		for(Board board : boards){
			System.out.println(board.getTitle());
		}

		System.out.println("---------------------------------");
	}

	@Test
	public void test3(){
		List<Board> list = boardRepository.findAllByName("kim");
		for(Board board : list){
			System.out.println(board.getTitle());
		}
	}

	@Test
	public void test4(){
		List<Board> list = boardRepository.findAll();
		for(Board board : list){
			System.out.println(board.getTitle());
			System.out.println(board.getCategory().getName());
		};
	}

	@Test
	public void test5(){
		List<Board> list = boardRepository.getBoards();
		for(Board board : list){
			System.out.println(board.getTitle());
			System.out.println(board.getCategory().getName());
		};
	}
}
