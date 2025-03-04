package com.app.OrderService.Entity;

import com.app.OrderService.Enum.RestaurantStatus;
import com.app.OrderService.Model.ItemModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document
@Builder
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
    Map<Long,ItemModel> menu = new HashMap<>();
    String address;
    String phone;
    RestaurantStatus status;
}
