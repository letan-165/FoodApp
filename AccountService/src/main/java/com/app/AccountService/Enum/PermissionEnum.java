package com.app.AccountService.Enum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@AllArgsConstructor
public enum PermissionEnum {
    READ("READ"),
    UPDATE("UPDATE"),
    CREATE("CREATE"),
    DELETE("DELETE");

    String name;
}
