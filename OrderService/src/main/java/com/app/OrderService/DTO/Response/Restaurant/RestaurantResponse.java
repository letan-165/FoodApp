package com.app.OrderService.DTO.Response.Restaurant;

import com.app.OrderService.DTO.BaseDTO.ItemDTO;
import com.app.OrderService.Enum.RestaurantStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestaurantResponse {
    String restaurantID;
    String userID;
    String name;

    @Builder.Default
    List<ItemDTO> menu = new ArrayList<>();
    String address;
    String phone;
    RestaurantStatus status;
}
