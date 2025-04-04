package com.app.OtherService.Config.WebSocket;

import com.app.OtherService.DTO.Request.Chat.ChatRequest;
import com.app.OtherService.DTO.Request.Client.TokenRequest;
import com.app.OtherService.DTO.Response.Chat.ChatResponse;
import com.app.OtherService.Repository.HttpClient.UserClient;
import com.app.OtherService.Service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatHandler extends TextWebSocketHandler {
    ChatService chatService;
    UserClient userClient;
    Map<String,WebSocketSession > webSocketSessionMap = new HashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ChatRequest request = objectMapper.readValue(message.getPayload(),ChatRequest.class);
            ChatResponse chatResponse = chatService.boxChat(request);
            String toJson = objectMapper.writeValueAsString(chatResponse);

            WebSocketSession sendUser1 = webSocketSessionMap.get(request.getUser());
            if(sendUser1!=null)
                sendUser1.sendMessage(new TextMessage((toJson)));

            WebSocketSession sendUser2 = webSocketSessionMap.get(request.getUser2());
            if(sendUser2!=null)
                sendUser2.sendMessage(new TextMessage((toJson)));

            session.sendMessage(new TextMessage("Gửi tin nhắn "+ chatResponse.getId() +" thành công"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userID = getUserID(session);
        if(!userID.isEmpty())
             webSocketSessionMap.put(userID,session);
        System.out.println("Mở kết nối chat: " + userID);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userID = getUserID(session);
        if(!userID.isEmpty())
            webSocketSessionMap.remove(userID);
        System.out.println("Đóng kết nối chat: " + userID);
    }


    String getUserID(WebSocketSession session){
        String headerAuthorization = session.getHandshakeHeaders().getFirst("Authorization");
        if(headerAuthorization==null)
            return "";

        String token = headerAuthorization.replace("Bearer ","");

        return userClient.getUserIDFromToken(TokenRequest.builder()
                .token(token)
                .build()).getResult();
    }
}
