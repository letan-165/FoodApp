package com.app.OrderService.Entity;

import com.app.OrderService.DTO.BaseDTO.ItemCartDTO;
import com.app.OrderService.DTO.BaseDTO.ItemDTO;
import com.app.OrderService.Enum.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.*;

@Document
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    String orderID;
    String customerID;
    String restaurantID;
    String shipperID;

    @Builder.Default
    Map<Long, ItemDTO> menu = new HashMap<>();

    Long total;
    LocalDateTime time;
    OrderStatus status;
}
