package com.app.OrderService.Enum;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum OrderStatus {
    PENDING("PENDING"),
    CONFIRMED("CONFIRMED"),
    ON_DELIVERY("ON_DELIVERY"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED");

    String name;

}
