package com.app.OrderService.DTO.Request.Order;

import com.app.OrderService.Enum.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderUpdateStatusRequest {
    String shipperID;
    OrderStatus status;
}
