package com.app.OtherService.DTO.Request.Chat;

import com.app.OtherService.DTO.BaseDTO.MessageDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatRequest {
    String id;
    String user;
    String user2;
    MessageDTO message;
}
