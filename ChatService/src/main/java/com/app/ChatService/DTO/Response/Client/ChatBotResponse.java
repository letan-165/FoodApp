package com.app.ChatService.DTO.Response.Client;

import com.app.ChatService.DTO.Response.Chat.ChatResponse;
import com.app.ChatService.DTO.Response.Client.UsageChatBotResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatBotResponse extends ChatResponse {
    String object;
    long created;
    String model;
    UsageChatBotResponse usage;
}
