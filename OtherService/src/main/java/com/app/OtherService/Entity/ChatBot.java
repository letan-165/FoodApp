package com.app.OtherService.Entity;

import com.app.OtherService.DTO.BaseDTO.MessageDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatBot {
    @Id
    String user;
    List<MessageDTO> messages;
}
