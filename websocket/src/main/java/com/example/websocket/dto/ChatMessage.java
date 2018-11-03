package com.example.websocket.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private Long chatRoomId;
    private String message;
    private String name;
}
