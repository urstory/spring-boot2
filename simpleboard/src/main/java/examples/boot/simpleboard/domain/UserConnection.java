package examples.boot.simpleboard.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_connection")
@Getter
@Setter
@NoArgsConstructor
public class UserConnection implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Size(max = 255)
    @NotNull
    private String providerId;

    @Size(max = 255)
    private String providerUserId;

    @NotNull
    private int rank;

    @Size(max = 255)
    private String displayName;

    @Size(max = 512)
    private String profileUrl;

    @Size(max = 512)
    private String imageUrl;

    @NotNull
    @Size(max = 512)
    private String accessToken;

    @Size(max = 512)
    private String secret;

    @Size(max = 512)
    private String refreshToken;

    private long expireTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    User user;

    // 헬퍼메소드
    public void setUser(User user){
        this.user = user;
        if(!user.getUserConnections().contains(this)){
            user.getUserConnections().add(this);
        }
    }

}
