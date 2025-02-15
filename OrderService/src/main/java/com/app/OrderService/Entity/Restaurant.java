package com.app.OrderService.Entity;

import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Restaurant {
    @Id
    Long restaurantID;
    String userID;
    String name;
    List<Item> menu;
    String address;
    String phone;
    String status;
}
