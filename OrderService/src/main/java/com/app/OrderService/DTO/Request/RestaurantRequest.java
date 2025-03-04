package com.app.OrderService.DTO.Request;

import com.app.OrderService.Model.ItemModel;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestaurantRequest {
    String userID;
    String name;
    String address;
    String phone;
    String status;
}
