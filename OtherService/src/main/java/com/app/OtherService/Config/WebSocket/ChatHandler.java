package com.app.OtherService.Config.WebSocket;

import com.app.OtherService.DTO.Request.Chat.ChatRequest;
import com.app.OtherService.DTO.Response.Chat.ChatResponse;
import com.app.OtherService.Service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {

    private final ChatService chatService;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ChatRequest request = objectMapper.readValue(message.getPayload(),ChatRequest.class);
            ChatResponse chatResponse = chatService.boxChat(request);
            String toJson = objectMapper.writeValueAsString(chatResponse);
            session.sendMessage(new TextMessage(toJson));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Mở kết nối chat: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Đóng kết nối chat: " + session.getId());
    }
}
