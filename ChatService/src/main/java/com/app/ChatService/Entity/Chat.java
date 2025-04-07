package com.app.ChatService.Entity;

import com.app.ChatService.DTO.Response.Client.ChoicesChatBotResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chat {
    @Id
    String id;
    String user;
    String user2;
    List<ChoicesChatBotResponse> choices = new ArrayList<>();
}
