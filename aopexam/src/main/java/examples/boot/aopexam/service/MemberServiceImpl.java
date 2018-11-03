package examples.boot.aopexam.service;

import examples.boot.aopexam.dto.Member;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// 서비스의 메소드가 실행될 때마다 메소드의 이름을 출력하시오.
@Service
public class MemberServiceImpl implements MemberService{
    @Override
    public Member getMember(Long id) {
        Member member = new Member(id, "kim", 10);
        return member;
    }

    @Override
    public List<Member> getMembers() {
        List<Member> list = new ArrayList<>();
        list.add(new Member(1L, "kim", 10));
        list.add(new Member(2L, "kang", 20));
        if(1 == 1)
            throw new RuntimeException("1 == 1");
        list.add(new Member(3L, "hong", 15));
        list.add(new Member(4L, "choi", 23));
        return list;
    }
}
