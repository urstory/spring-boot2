package examples.boot.simpleboard.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

import javax.jdo.annotations.Join;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chat_user")
@Getter
@Setter
public class ChatUser implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    @JsonBackReference
    ChatRoom chatRoom;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    User user;

    // 헬퍼메소드
    public void setChatRoom(ChatRoom chatRoom){
        this.chatRoom = chatRoom;
        if(!chatRoom.getChatUsers().contains(this)){
            chatRoom.getChatUsers().add(this);
        }
    }
}
