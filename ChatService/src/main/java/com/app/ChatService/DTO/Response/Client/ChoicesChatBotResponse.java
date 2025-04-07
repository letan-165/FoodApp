package com.app.ChatService.DTO.Response.Client;

import com.app.ChatService.DTO.BaseDTO.MessageDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChoicesChatBotResponse {
    Long index;
    MessageDTO message;
    String finish_reason;
}
