package com.app.OtherService.DTO.Response.Chat;

import com.app.OtherService.DTO.Response.Client.ChoicesChatBotResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatResponse {
    String id;
    String user;
    String user2;
    List<ChoicesChatBotResponse> choices;
}
