package com.app.OrderService.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RestaurantStatus {
    OPEN("OPEN"),
    CLOSED("CLOSED");
    String name;
}
