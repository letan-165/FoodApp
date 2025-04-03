package com.app.OtherService.DTO.Response.Client;

import com.app.OtherService.DTO.Response.Chat.ChatResponse;
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
