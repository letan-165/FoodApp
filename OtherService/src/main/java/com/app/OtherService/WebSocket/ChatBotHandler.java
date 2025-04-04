package com.app.OtherService.WebSocket;


import com.app.OtherService.DTO.Request.ChatBot.SendChatBotRequest;
import com.app.OtherService.Entity.ChatBot;
import com.app.OtherService.Service.ChatBotService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ChatBotHandler extends TextWebSocketHandler {
    ChatBotService chatBotService;
    ObjectMapper objectMapper;
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try{
            String userID = (String) session.getAttributes().get("userID");
            SendChatBotRequest request = objectMapper.readValue(message.getPayload(), SendChatBotRequest.class);

            ChatBot chatBot = chatBotService.sendChatBot(userID, request);

            String response = objectMapper.writeValueAsString(chatBot);

            session.sendMessage(new TextMessage(response));

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userID = (String) session.getAttributes().get("userID");
        ChatBot chatBot = chatBotService.createChatBot(userID);
        System.out.println("ChatBot đã được kết nối với "+ chatBot.toString());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userID = (String) session.getAttributes().get("userID");
        chatBotService.deleteChatBot(userID);
        System.out.println("ChatBot đã được đóng với "+userID);
    }
}
