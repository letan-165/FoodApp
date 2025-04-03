package com.app.OtherService.DTO.Request.Client;

import com.app.OtherService.DTO.BaseDTO.MessageDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatBotRequest {
    String model;
    List<MessageDTO> messages;
}
