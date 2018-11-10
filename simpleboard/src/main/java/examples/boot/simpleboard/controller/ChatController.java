package examples.boot.simpleboard.controller;

import examples.boot.simpleboard.domain.ChatRoom;
import examples.boot.simpleboard.domain.ChatUser;
import examples.boot.simpleboard.domain.User;
import examples.boot.simpleboard.dto.ChatMessage;
import examples.boot.simpleboard.service.ChatService;
import examples.boot.simpleboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/chatrooms")
public class ChatController {
    @Autowired
    ChatService chatService;

    @Autowired
    UserService userService;

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/rooms/{chatRoomId}")
    public void sendMessage(@DestinationVariable Long chatRoomId, ChatMessage message, Principal principal) throws Exception {
        //TODO 해당 방에 입장된 사람일 경우에만 아래의 코드가 실행되도록 한다.
        User user = userService.getUserByEmail(principal.getName());
        message.setName(user.getName());
        this.template.convertAndSend("/topic/rooms/"+chatRoomId, message);
//        return message; // 리턴한 값도 전송된다.
    }


    @GetMapping
    public String chatRooms(ModelMap modelMap){

        List<ChatRoom> list = chatService.getChatRooms();
        modelMap.put("list", list);

        return "chat/chatrooms";
    }

    @GetMapping(path = "/{id}")
    public String chatroom(Principal principal,  @PathVariable(name = "id") Long id, ModelMap modelMap){
        User user = userService.getUserByEmail(principal.getName());
        ChatRoom chatRoom = chatService.getChatRoom(id);
        List<ChatUser> chatUserList = chatRoom.getChatUsers();

        boolean findChatUser = false;
        for(ChatUser chatUser : chatUserList){
            if(chatUser.getUser().getId() == user.getId()){
                break;
            }else{
                findChatUser = true;
                break;
            }
        }

        if(!findChatUser){
            ChatUser chatUser = new ChatUser();
            chatUser.setChatRoom(chatRoom);
            chatService.addChatUser(chatUser);
        }

        modelMap.addAttribute("chatRoom", chatRoom);
        return "chat/chatroom";
    }

    @PostMapping
    public String create(Principal principal, @RequestParam(name = "title")String title){
        User user = userService.getUserByEmail(principal.getName());
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setTitle(title);

        ChatUser chatUser = new ChatUser();
        chatUser.setUser(user);
        chatUser.setChatRoom(chatRoom);

        chatService.addChatRoom(chatRoom);
        return "redirect:/chatrooms";
    }

    @GetMapping("/createform")
    public String create(){
        return "chat/createform";
    }
}
