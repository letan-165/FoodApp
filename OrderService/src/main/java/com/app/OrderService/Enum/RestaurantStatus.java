package com.app.OrderService.Enum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public enum RestaurantStatus {
    OPEN("OPEN"),
    CLOSED("CLOSED"),
    BUSY("BUSY"),
    PENDING("PENDING");
    String name;
}
