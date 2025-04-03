package com.app.OtherService.Entity;

import com.app.OtherService.DTO.Response.Client.ChoicesChatBotResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Document
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chat {
    String id;
    String user;
    String user2;
    List<ChoicesChatBotResponse> choices = new ArrayList<>();
}
