package com.app.ChatService.Mapper;

import com.app.ChatService.DTO.Request.Chat.SendChatRequest;
import com.app.ChatService.DTO.Response.Client.ChatBotResponse;
import com.app.ChatService.DTO.Response.Chat.ChatResponse;
import com.app.ChatService.Entity.Chat;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    ChatResponse toChatResponse(ChatBotResponse response);
    ChatResponse toChatResponse(Chat chat);

    Chat toChat(SendChatRequest request);
}
