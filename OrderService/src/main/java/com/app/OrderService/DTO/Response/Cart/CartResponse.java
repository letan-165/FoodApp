package com.app.OrderService.DTO.Response.Cart;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
    String userID;

    @Builder.Default
    List<RestaurantCartResponse> restaurants = new ArrayList<>(Collections.emptyList());
}
