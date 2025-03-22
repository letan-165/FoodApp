package com.app.OrderService.Entity;

import com.app.OrderService.DTO.BaseDTO.RestaurantCartEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart {
    @Id
    String userID;
    Map<String, RestaurantCartEntity> restaurants = new HashMap<>();
}
