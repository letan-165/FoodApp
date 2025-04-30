package com.app.AccountService.DTO.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    @Size(min = 4,max = 10, message = "USERNAME_INVALID")
    String name;
    @Size(min = 1, max = 10,message = "PASSWORD_INVALID")
    String password;

    String phone;

    @Email(message = "GMAIL_INVALID")
    String gmail;

    int otp;

    @Builder.Default
    List<String> roles = new ArrayList<>(Collections.emptyList());;
}
