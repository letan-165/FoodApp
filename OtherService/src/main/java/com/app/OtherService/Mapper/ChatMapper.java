package com.app.OtherService.Mapper;

import com.app.OtherService.DTO.Request.Chat.ChatRequest;
import com.app.OtherService.DTO.Response.Client.ChatBotResponse;
import com.app.OtherService.DTO.Response.Chat.ChatResponse;
import com.app.OtherService.Entity.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    ChatResponse toChatResponse(ChatBotResponse response);
    ChatResponse toChatResponse(Chat chat);

    Chat toChat(ChatRequest request);
}
