package examples.boot.simpleboard;

import examples.boot.simpleboard.domain.User;
import examples.boot.simpleboard.domain.UserRole;
import examples.boot.simpleboard.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    public void test() throws Exception{
        List<User> allUsers = userRepository.getAllUsers();
        for(User u : allUsers){
            System.out.println(u.getName());
            System.out.println(u.getRoles().size());
            System.out.println("==========================");
        }
    }

}
