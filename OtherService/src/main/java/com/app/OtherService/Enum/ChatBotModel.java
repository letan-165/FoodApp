package com.app.OtherService.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatBotModel {
    GEMINIPRO_2_5("google/gemini-2.5-pro-exp-03-25:free"),
    DEEPSEAK_V3("deepseek/deepseek-chat-v3-0324:free")
    ;

    String model;
}
