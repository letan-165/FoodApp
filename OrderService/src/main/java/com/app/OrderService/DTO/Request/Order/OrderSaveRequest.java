package com.app.OrderService.DTO.Request.Order;

import com.app.OrderService.DTO.BaseDTO.ItemCartEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderSaveRequest {
    String customerID;
    String restaurantID;
    String paymentID;
    List<ItemCartEntity> menu;

    String address;
    String phone;
}
