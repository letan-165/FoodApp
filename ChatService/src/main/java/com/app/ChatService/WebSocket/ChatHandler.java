package com.app.ChatService.WebSocket;

import com.app.ChatService.DTO.Request.Chat.SendChatRequest;
import com.app.ChatService.DTO.Response.Chat.ChatResponse;
import com.app.ChatService.Repository.HttpClient.UserClient;
import com.app.ChatService.Service.ChatService;
import com.fasterxml.jackson.databind.JsonNode;
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
            SendChatRequest request = objectMapper.readValue(message.getPayload(), SendChatRequest.class);

            switch (request.getAction()){
                case "CREATE":
                    request.setId(chatService.createChat(request.getCreate()));
                    break;
                case "SEND":
                    chatService.sendChat(request.getId(),request.getSend());
                    break;
                case "RECALL":
                    chatService.recallMessage(request.getId(),request.getRecall());
                    break;
                default:
                 throw new RuntimeException("Action errol");
            }

            ChatResponse chatResponse = chatService.findChat(request.getId());
            String toJson = objectMapper.writeValueAsString(chatResponse);
            WebSocketSession sendUser1 = webSocketSessionMap.get(chatResponse.getUser());

            if(sendUser1!=null)
                sendUser1.sendMessage(new TextMessage((toJson)));

            WebSocketSession sendUser2 = webSocketSessionMap.get(chatResponse.getUser2());
            if(sendUser2!=null)
                sendUser2.sendMessage(new TextMessage((toJson)));

            session.sendMessage(new TextMessage("Gửi tin nhắn "+ chatResponse.getId() +" thành công"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userID = (String) session.getAttributes().get("userID");
        if(userID != null && !userID.isEmpty())
             webSocketSessionMap.put(userID,session);

        System.out.println("Mở kết nối chat: " + userID);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userID = (String) session.getAttributes().get("userID");
        if(userID != null && !userID.isEmpty())
            webSocketSessionMap.remove(userID);
        System.out.println("Đóng kết nối chat: " + userID);
    }

}
