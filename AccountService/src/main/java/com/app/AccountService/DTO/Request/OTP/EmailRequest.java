package com.app.AccountService.DTO.Request.OTP;

import com.app.AccountService.Exception.ErrorCode;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailRequest {
    @Email(message = "GMAIL_INVALID")
    String email;
}
