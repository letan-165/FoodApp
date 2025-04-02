package com.app.OtherService.Service;

import com.app.OtherService.DTO.Request.Client.ChatBotRequest;
import com.app.OtherService.DTO.Response.Client.ChatBotResponse;
import com.app.OtherService.Enum.ChatBotModel;
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

    @NonFinal
    @Value("${chatbot.key}")
    String keyChatBot;

    public ChatBotResponse sendChatBot(ChatBotRequest request){
        request.setModel(ChatBotModel.DEEPSEAK_V3.getModel());
        return chatBotClient.sendChatBot("Bearer " + keyChatBot,request);
    };
}
