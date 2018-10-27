package examples.boot.myshop.repository;

import examples.boot.myshop.entity.Member;
import examples.boot.myshop.entity.Role;
import examples.boot.myshop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

// junit이 제공하는 확장점.
@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void test(){
        // 아무런 일도 안하는 메소드는 스프링 설정이 올바른지만 검사한다.
    }

    @Test
    public void getMember1() throws Exception{
        Member member = memberRepository.findMemberByEmail("urstory@gmail.com");
        Assert.assertNotNull(member); // null이 아니면 성공.
        Assert.assertEquals("kim", member.getName());

        System.out.println("---------------------");
        Set<Role> roles = member.getRoles();
        System.out.println("---------------------");

        for(Role role:roles){
            System.out.println(role.getName());
        }
    }

    @Test
    public void getMember2() throws Exception{
        Member member = memberRepository.getMemberAndRoles("urstory@gmail.com");
        Assert.assertNotNull(member); // null이 아니면 성공.
        Assert.assertEquals("kim", member.getName());

        System.out.println("---------------------");
        Set<Role> roles = member.getRoles();
        System.out.println("---------------------");

        for(Role role:roles){
            System.out.println(role.getName());
        }
    }
}
