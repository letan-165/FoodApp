package com.app.OrderService.DTO.Request;

import com.app.OrderService.Entity.Item;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    @Id
    Long orderID;
    String customerID;
    String restaurantID;
    String shipperID;
    List<Item> menu;
    Long total;
    LocalDateTime time;
    String status;
}
