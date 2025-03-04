package com.app.OrderService.Entity;

import com.app.OrderService.Enum.OrderStatus;
import com.app.OrderService.Model.ItemModel;
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
    Map<Long,ItemModel> menu = new HashMap<>();

    Long total;
    LocalDateTime time;
    OrderStatus status;
}
