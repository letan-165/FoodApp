package com.app.AccountService.DTO.Request.OTP;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OTPRequest {
    String email;
    int otp;
}
