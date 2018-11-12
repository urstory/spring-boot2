package examples.boot.aopexam;

import examples.boot.aopexam.controller.MemberController;
import examples.boot.aopexam.dto.Member;
import examples.boot.aopexam.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.omg.IOP.ExceptionDetailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AopexamApplicationTests {
    @InjectMocks
    MemberController memberController;

    @Mock
    MemberService memberService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testGetMember() throws Exception{
        Member member = new Member(1L, "kim", 50);
        when(memberService.getMember(anyLong())).thenReturn(member);

        Member result = memberController.getMember(1L);
        System.out.println(result.getName());
    }

}
