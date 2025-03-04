package com.app.OrderService.Entity;

import com.app.OrderService.Model.ContentModel;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message {
    @Id
    String messageID;
    String betweenUser;
    String andUser;
    List<ContentModel> contents;
}
