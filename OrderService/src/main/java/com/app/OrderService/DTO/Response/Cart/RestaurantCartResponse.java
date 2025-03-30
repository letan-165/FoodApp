package com.app.OrderService.DTO.Response.Cart;

import com.app.OrderService.DTO.BaseDTO.ItemCartEntity;
import com.app.OrderService.DTO.BaseDTO.ItemEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestaurantCartResponse {
    String restaurantID;
    String name;

    @Builder.Default
    List<ItemEntity> menu = new ArrayList<>();
}
