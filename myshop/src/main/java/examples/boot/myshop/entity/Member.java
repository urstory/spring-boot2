package examples.boot.myshop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "member")
@Setter
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;
    String name;
    String email;
    String password;
    LocalDateTime regdate;

//    @ManyToMany(cascade = {CascadeType.ALL})
    @ManyToMany
    @JoinTable(name = "member_member_role",
            joinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id") ,
            inverseJoinColumns = @JoinColumn(name = "member_role_id", referencedColumnName = "id") )
    Set<Role> roles;

    public Member(){
        this.regdate = LocalDateTime.now();
    }

    // helper method
    public void addRole(Role role){
        if(roles == null){
            roles = new HashSet<>();
        }
        roles.add(role);
    }
}

/*

Set<Role> roles = new HashSet<>();
roles.add( role );
Member member = new Member();
member.setRoles(roles)

Member member = ....;
Set<Role> roles = member.getRoles();
roles.add( 추가하고자하는role);
member.setRoles(roles);
 */
