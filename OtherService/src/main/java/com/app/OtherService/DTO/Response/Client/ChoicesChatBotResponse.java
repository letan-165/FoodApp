package com.app.OtherService.DTO.Response.Client;

import com.app.OtherService.DTO.BaseDTO.MessageChatBot;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChoicesChatBotResponse {
    Long index;
    MessageChatBot message;
    String finish_reason;
}
