package com.app.OtherService.DTO.Response.Client;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatBotResponse {
    String id;
    String object;
    long created;
    String model;
    List<ChoicesChatBotResponse> choices;
    UsageChatBotResponse usage;
}
