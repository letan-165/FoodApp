package com.app.OrderService.DTO.BaseDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestaurantCartDTO {
    String restaurantID;

    @Builder.Default
    Map<Long ,ItemCartDTO> menu = new HashMap<>();
}
