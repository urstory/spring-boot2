package com.example.websocket.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SocketHandler extends TextWebSocketHandler {
    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {

        System.out.println(message.getPayload());

        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};
        HashMap<String, String> map = objectMapper.readValue(message.getPayload(), typeRef);
        map.put("name", "loginname"); // login 정보를 어떻게 읽어와야할지 고민하자.

        String msg = objectMapper.writeValueAsString(map);
        System.out.println(msg);
        TextMessage textMessage = new TextMessage(msg);

        sessions.stream().forEach(s -> {
            try {
                s.sendMessage(textMessage);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        });
    }

    // 클라이언트가 접속을 하면 클라이언트와 통신할 수 있는 WebSocketSession이 전달된다.
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        InetSocketAddress clientAddress = session.getRemoteAddress();
        HttpHeaders handshakeHeaders = session.getHandshakeHeaders();

//        logger.info("Handshake header: Accept {}", handshakeHeaders.toString());
//        logger.info("Handshake header: User-Agent {}", handshakeHeaders.get("User-Agent").toString());
//        logger.info("Handshake header: Sec-WebSocket-Extensions {}", handshakeHeaders.get("Sec-WebSocket-Extensions").toString());
//        logger.info("Handshake header: Sec-WebSocket-Key {}", handshakeHeaders.get("Sec-WebSocket-Key").toString());
//        logger.info("Handshake header: Sec-WebSocket-Version {}", handshakeHeaders.get("Sec-WebSocket-Version").toString());
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        System.out.println(sessions.size());
        System.out.println("session remove");
        sessions.remove(session);
        System.out.println(sessions.size());
    }
}