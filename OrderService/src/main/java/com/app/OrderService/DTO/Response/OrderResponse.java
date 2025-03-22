package com.app.OrderService.DTO.Response;

import com.app.OrderService.DTO.BaseDTO.ItemEntity;
import com.app.OrderService.Enum.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Builder.Default
    List<ItemEntity> menu = new ArrayList<>();
    Long total;
    Instant time;
    OrderStatus status;
}
