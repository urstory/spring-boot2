package examples.boot.myshop.security;

import examples.boot.myshop.entity.Member;
import examples.boot.myshop.entity.Role;
import examples.boot.myshop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class MyShopUserDetailsService implements UserDetailsService {
    @Autowired
    MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.getMemberAndRoles(email);
        if(member == null){
            throw new UsernameNotFoundException(email + " is not found");
        }
        String password = member.getPassword();
        Collection<GrantedAuthority> authorities = new HashSet<>();
        Set<Role> roles = member.getRoles();
        for(Role role:roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        LoginUser loginUser = new LoginUser(email, password, authorities);
        loginUser.setId(member.getId());
        loginUser.setName(member.getName());
        return loginUser;
    }
}
