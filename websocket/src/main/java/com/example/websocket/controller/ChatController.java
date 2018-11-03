package com.example.websocket.controller;

import com.example.websocket.dto.ChatMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/chatrooms")
public class ChatController {

    @GetMapping(path = "/{id}")
    public String chatroom(Principal principal,  @PathVariable(name = "id") Long id, ModelMap modelMap){
        modelMap.addAttribute("chatRoomId", id);
        return "chat/chatroom";
    }
}
