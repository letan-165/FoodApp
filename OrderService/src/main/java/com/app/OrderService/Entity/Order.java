package com.app.OrderService.Entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

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
    List<Item> menu;
    Long total;
    LocalDateTime time;
    String status;
}
