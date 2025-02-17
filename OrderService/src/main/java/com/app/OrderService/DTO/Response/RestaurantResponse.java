package com.app.OrderService.DTO.Response;

import com.app.OrderService.Entity.Item;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestaurantResponse {
    @Id
    Long restaurantID;
    String userID;
    String name;
    List<Item> menu;
    String address;
    String phone;
    String status;
}
