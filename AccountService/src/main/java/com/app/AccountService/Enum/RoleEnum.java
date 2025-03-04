package com.app.AccountService.Enum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@AllArgsConstructor
public enum RoleEnum {
    CUSTOMER("CUSTOMER"),
    SHIPPER("SHIPPER"),
    ADMIN("ADMIN"),
    RESTAURANT("RESTAURANT");

    String name;
}
