package com.app.OtherService.DTO.Response.Client;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsageChatBotResponse {
    Long prompt_tokens;
    Long completion_tokens;
    Long total_tokens;
}
