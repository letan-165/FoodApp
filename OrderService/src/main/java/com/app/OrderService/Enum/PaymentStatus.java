package com.app.OrderService.Enum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public enum PaymentStatus {
    PENDING("PENDING"),
    FAILED("FAILED"),
    SUCCESS("SUCCESS");

    String name;
}
