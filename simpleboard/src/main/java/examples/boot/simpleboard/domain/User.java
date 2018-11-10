package examples.boot.simpleboard.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User implements Serializable {
    public User(){
        regdate = LocalDateTime.now();
    }
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @JsonIgnore
    private String password;
    private LocalDateTime regdate;

    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JsonManagedReference
    private List<UserRole> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JsonManagedReference
    private List<UserConnection> userConnections = new ArrayList<>();

    // 헬퍼 메소드. User에 UserRole을 추가할때 사용한다.
    public void addUserRole(UserRole role){
        this.roles.add(role);
        if(role.getUser() != this){
            role.setUser(this);
        }
    }

    public void addUserConnection(UserConnection userConnection){
        this.userConnections.add(userConnection);
        if(userConnection.getUser() != this){
            userConnection.setUser(this);
        }
    }
}
