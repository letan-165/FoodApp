package com.app.OrderService.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethod {
    CASH("CASH"),
    MOMO("MOMO");
    String name;
}
