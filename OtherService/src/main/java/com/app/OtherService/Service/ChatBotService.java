package com.app.OtherService.Service;

import com.app.OtherService.DTO.Request.Client.ChatBotRequest;
import com.app.OtherService.DTO.Response.Chat.ChatResponse;
import com.app.OtherService.Enum.ModelChatBot;
import com.app.OtherService.Mapper.ChatMapper;
import com.app.OtherService.Repository.HttpClient.ChatBotClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class ChatBotService {
    ChatBotClient chatBotClient;
    ChatMapper messageMapper;

    @NonFinal
    @Value("${chatbot.key}")
    String keyChatBot;

    public ChatResponse sendChatBot(ChatBotRequest request){
        request.setModel(ModelChatBot.DEEPSEAK_V3.getModel());
        ChatResponse messageResponse = messageMapper.toChatResponse(chatBotClient
                .sendChatBot("Bearer " + keyChatBot,request));
        messageResponse.setUser("user");
        messageResponse.setUser2("ChatBot");
        return messageResponse;
    };



}
