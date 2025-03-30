package com.app.OrderService.Entity;

import com.app.OrderService.DTO.BaseDTO.ItemEntity;
import com.app.OrderService.Enum.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Document
@Builder(toBuilder = true)
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
    String paymentID;

    @Builder.Default
    Map<Long, ItemEntity> menu = new HashMap<>();

    Long total;
    Instant time;
    OrderStatus status;
    String address;
    String phone;

}
