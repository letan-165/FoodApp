package com.app.OtherService.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleChatBot {
    USER("user"),
    ASSISTANT("assistant")
    ;

    String role;
}
