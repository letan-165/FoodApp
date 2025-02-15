package com.app.OrderService.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    PENDING("PENDING"),
    ON_DELIVERY("ON_DELIVERY"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED"),
    CONFIRMED("CONFIRMED");
    String name;

}
