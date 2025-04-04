package com.app.OtherService.Service;

import com.app.OtherService.DTO.Request.Chat.ChatRequest;
import com.app.OtherService.DTO.Response.Chat.ChatResponse;
import com.app.OtherService.DTO.Response.Client.ChoicesChatBotResponse;
import com.app.OtherService.Entity.Chat;
import com.app.OtherService.Exception.AppException;
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

    public ChatResponse boxChat(ChatRequest request){
        userClient.findById(request.getUser());
        userClient.findById(request.getUser2());
        String sender = request.getMessage().getRole();
        if(!sender.equals(request.getUser()) && !sender.equals(request.getUser2()))
            throw new RuntimeException("Lỗi do role không trùng lớp request user");

        Chat requestChat = chatMapper.toChat(request);
        Chat chat = (request.getId() == null)
            ? requestChat.toBuilder()
                .choices(new ArrayList<>())
                .build()
            : chatRepository.findById(request.getId()).orElseThrow(()-> new RuntimeException("Chat no exits"));

        long index = chat.getChoices().stream().mapToLong(ChoicesChatBotResponse::getIndex).max().orElse(0L);

        chat.getChoices().add(ChoicesChatBotResponse.builder()
                .index(index+1)
                .message(request.getMessage())
                .finish_reason("NO")
                .build());


        return chatMapper.toChatResponse(chatRepository.save(chat));
    }
}
