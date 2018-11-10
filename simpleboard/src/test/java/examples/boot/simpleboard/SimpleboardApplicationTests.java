//package examples.boot.simpleboard;
//
//import com.querydsl.core.Tuple;
//import examples.boot.simpleboard.domain.User;
//import examples.boot.simpleboard.repository.UserRepository;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.persistence.EntityManager;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class SimpleboardApplicationTests {
//	@Autowired
//	UserRepository userRepository;
//
//	@Autowired
//	EntityManager entityManager;
//
//	@Test
//	public void contextLoads() {
//	}
//
//	@Test
//	public void getUserByName() throws Exception{
//		// given
//		User user = new User();
//		user.setName("kim sungpark");
//		user.setPassword("1234");
//		user.setRegdate(LocalDateTime.now());
//		user.setEmail("urstory@gmail.com");
//		userRepository.save(user);
//
//		// when
//		User resultUser = userRepository.findUserByName("kim sungpark");
//
//		// then
//		Assert.assertEquals(user.getEmail(), resultUser.getEmail());
//
//		System.out.println(resultUser.getId());
//		System.out.println(resultUser.getEmail());
//		System.out.println(resultUser.getPassword());
//		System.out.println(resultUser.getRegdate());
//		System.out.println("--------------------------------------------");
//		List<Tuple> tupleList = userRepository.getUserCount(entityManager);
//		for(Tuple tuple:tupleList){
//			System.out.println(tuple.get(0, String.class));
//			System.out.println(tuple.get(1, Integer.class));
//		}
//		System.out.println("--------------------------------------------");
//		User resultUser2 = userRepository.findUserByName("kim sungpark");
//		System.out.println(resultUser2.getEmail());
//	}
//
//}
