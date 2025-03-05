package com.app.OrderService.DTO.Response;

import com.app.OrderService.Entity.Restaurant;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
    String userID;
    List<RestaurantResponse> restaurants;
}
