package com.app.OrderService.DTO.Response.Cart;

import com.app.OrderService.DTO.BaseDTO.ItemCartEntity;
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

    @Builder.Default
    List<ItemCartEntity> menu = new ArrayList<>();
}
