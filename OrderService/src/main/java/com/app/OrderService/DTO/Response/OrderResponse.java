package com.app.OrderService.DTO.Response;

import com.app.OrderService.Model.ItemModel;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    String orderID;
    String customerID;
    String restaurantID;
    String shipperID;
    List<ItemModel> menu;
    Long total;
    LocalDateTime time;
    String status;
}
