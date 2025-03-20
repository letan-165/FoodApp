package com.app.OrderService.DTO.Request.Restaurant;

import com.app.OrderService.Enum.RestaurantStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestaurantUpdateRequest extends RestaurantSaveRequest {
    RestaurantStatus status;
}
