package com.app.OrderService.Entity;

import com.app.OrderService.DTO.BaseDTO.ItemDTO;
import com.app.OrderService.Enum.RestaurantStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document
@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Restaurant {
    @Id
    String restaurantID;
    String userID;
    String name;

    @Builder.Default
    Map<Long, ItemDTO> menu = new HashMap<>();
    String address;
    String phone;
    RestaurantStatus status;
}
