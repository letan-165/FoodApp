package com.app.OrderService.DTO.BaseDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestaurantCartEntity {
    String restaurantID;

    @Builder.Default
    Map<Long , ItemCartEntity> menu = new HashMap<>();
}
