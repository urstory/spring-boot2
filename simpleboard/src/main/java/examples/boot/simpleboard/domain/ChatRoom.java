package examples.boot.simpleboard.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chat_room")
@Getter
@Setter
public class ChatRoom implements Serializable {
    public ChatRoom(){
        regdate = LocalDateTime.now();
    }
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String title;
    private LocalDateTime regdate = LocalDateTime.now();

    @OneToMany(mappedBy = "chatRoom", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JsonManagedReference
    private List<ChatUser> chatUsers = new ArrayList<>();

    public void addChatUser(ChatUser chatUser){
        this.chatUsers.add(chatUser);
        if(chatUser.getChatRoom() != this){
            chatUser.setChatRoom(this);
        }
    }
}
