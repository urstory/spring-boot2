package examples.boot.aopexam.service;

import examples.boot.aopexam.dto.Member;

import java.util.List;

public interface MemberService {
    public Member getMember(Long id);
    public List<Member> getMembers();
}
