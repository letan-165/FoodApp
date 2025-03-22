package com.app.OrderService.DTO.Request.Cart;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemCartRequest {
    String cartID;
    String restaurantID;
    Long itemID;
}
