package com.app.OtherService.DTO.Request.Chat;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateChatRequest {
    String user;
    String user2;
}
