package com.app.OrderService.DTO.Response;

import com.app.OrderService.Model.ItemModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantResponse {
    String restaurantID;
    String userID;
    String name;

    @Builder.Default
    List<ItemModel> menu = new ArrayList<>(Collections.emptyList());
    String address;
    String phone;
    String status;
}
