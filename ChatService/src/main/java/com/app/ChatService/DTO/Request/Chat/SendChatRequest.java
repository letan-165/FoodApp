package com.app.ChatService.DTO.Request.Chat;

import com.app.ChatService.DTO.BaseDTO.MessageDTO;
import com.app.ChatService.DTO.Request.Chat.CreateChatRequest;
import com.app.ChatService.DTO.Request.Chat.RecallMessageRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendChatRequest {
    String id;
    CreateChatRequest create;
    MessageDTO send;
    RecallMessageRequest recall;
    String action;
}
