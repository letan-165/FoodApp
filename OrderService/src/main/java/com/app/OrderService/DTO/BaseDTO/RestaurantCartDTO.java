package com.app.OrderService.DTO.BaseDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestaurantCartDTO {
    String restaurantID;
    List<ItemCartDTO> menu;
}
