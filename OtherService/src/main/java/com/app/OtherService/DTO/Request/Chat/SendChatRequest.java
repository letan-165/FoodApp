package com.app.OtherService.DTO.Request.Chat;

import com.app.OtherService.DTO.BaseDTO.MessageDTO;
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
