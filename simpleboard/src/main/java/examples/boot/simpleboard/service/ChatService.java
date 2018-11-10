package examples.boot.simpleboard.service;

import examples.boot.simpleboard.domain.ChatRoom;
import examples.boot.simpleboard.domain.ChatUser;

import java.util.List;

public interface ChatService {
    public List<ChatRoom> getChatRooms();
    public void addChatRoom(ChatRoom chatRoom);
    public void addChatUser(ChatUser chatUser);
    public ChatRoom getChatRoom(Long id);
}
