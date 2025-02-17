package com.app.OrderService.Entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Restaurant {
    @Id
    String restaurantID;
    String userID;
    String name;
    List<Item> menu;
    String address;
    String phone;
    String status;
}
