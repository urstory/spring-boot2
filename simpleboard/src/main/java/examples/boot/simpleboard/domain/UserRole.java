package examples.boot.simpleboard.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
@Getter
@Setter
public class UserRole {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    User user;

    // 헬퍼메소드
    public void setUser(User user){
        this.user = user;
        if(!user.getRoles().contains(this)){
            user.getRoles().add(this);
        }
    }
}
