package com.app.OtherService.Service;

import com.app.OtherService.DTO.BaseDTO.MessageDTO;
import com.app.OtherService.DTO.Request.Chat.CreateChatRequest;
import com.app.OtherService.DTO.Request.Chat.RecallMessageRequest;
import com.app.OtherService.DTO.Request.Chat.SendChatRequest;
import com.app.OtherService.DTO.Response.Chat.ChatResponse;
import com.app.OtherService.DTO.Response.Client.ChoicesChatBotResponse;
import com.app.OtherService.Entity.Chat;
import com.app.OtherService.Mapper.ChatMapper;
import com.app.OtherService.Repository.ChatRepository;
import com.app.OtherService.Repository.HttpClient.UserClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class ChatService {
    ChatRepository chatRepository;
    ChatMapper chatMapper;
    UserClient userClient;


    public String createChat(CreateChatRequest request){
        userClient.findById(request.getUser());
        userClient.findById(request.getUser2());

        Chat chat = chatRepository.save(Chat.builder()
                        .user(request.getUser())
                        .user2(request.getUser2())
                        .choices(new ArrayList<>())
                .build());
        return chat.getId();
    }

    public void sendChat(String chatID, MessageDTO message){
        Chat chat = chatRepository.findById(chatID)
                .orElseThrow(()-> new RuntimeException("Chat no exits"));

        String sender = message.getRole();
        if(!sender.equals(chat.getUser()) && !sender.equals(chat.getUser2()))
            throw new RuntimeException("Lỗi do role không trùng lớp chat user");

        long index = chat.getChoices().stream()
                .mapToLong(ChoicesChatBotResponse::getIndex)
                .max()
                .orElse(-1L);

        chat.getChoices().add(ChoicesChatBotResponse.builder()
                .index(index+1)
                .message(message)
                .finish_reason("NO")
                .build());

        chatRepository.save(chat);
    }

    public void recallMessage(String chatID,RecallMessageRequest request){
        Chat chat = chatRepository.findById(chatID)
                .orElseThrow(()->new RuntimeException("Chat no exists"));

        if(!request.getUserID().equals(chat.getUser()) && !request.getUserID().equals(chat.getUser2()))
            throw new RuntimeException("User recall unvalid ");

        if(!request.getUserID().equals(chat.getChoices().get(request.getMessageIndex()).getMessage().getRole()))
            throw new RuntimeException("User not permission recall ");

        chat.getChoices().get(request.getMessageIndex()).getMessage().setContent("Tin nhắn đã bị thu hồi");
        chatRepository.save(chat);
    }


    public ChatResponse findChat(String chatID){
        return chatMapper.toChatResponse(chatRepository.findById(chatID)
                .orElseThrow(()->new RuntimeException("Chat no exists")));
    }
}
