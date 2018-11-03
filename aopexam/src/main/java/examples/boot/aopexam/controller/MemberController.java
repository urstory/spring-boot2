package examples.boot.aopexam.controller;

import examples.boot.aopexam.dto.Member;
import examples.boot.aopexam.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    @Autowired
    MemberService memberService;

    @GetMapping
    public List<Member> getMembers(){
        // MemberServiceImpl이 아니라 MemberServiceImpl$$EnhancerBySpringCGLIB$$ba20f54f
        System.out.println(memberService.getClass().getName());
        return memberService.getMembers();
    }

    @GetMapping(path="/{id}")
    public Member getMember(@PathVariable(name="id") Long id){
        return memberService.getMember(id);
    }
}
