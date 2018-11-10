package examples.boot.simpleboard.service.impl;

import examples.boot.simpleboard.domain.ChatRoom;
import examples.boot.simpleboard.domain.ChatUser;
import examples.boot.simpleboard.repository.ChatRoomRepository;
import examples.boot.simpleboard.repository.ChatUserRepository;
import examples.boot.simpleboard.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Autowired
    ChatUserRepository chatUserRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ChatRoom> getChatRooms() {
        return chatRoomRepository.findAll();
    }

    @Override
    @Transactional
    public void addChatRoom(ChatRoom chatRoom) {
       chatRoomRepository.save(chatRoom);
    }

    @Override
    @Transactional
    public void addChatUser(ChatUser chatUser) {
        chatUserRepository.save(chatUser);
    }

    @Override
    @Transactional(readOnly = true)
    public ChatRoom getChatRoom(Long id) {
        return chatRoomRepository.getOne(id);
    }
}
