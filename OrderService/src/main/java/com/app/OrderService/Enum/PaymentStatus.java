package com.app.OrderService.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {
    PENDING("PENDING"),
    FAILED("FAILED"),
    SUCCESS("SUCCESS");

    String name;
}
