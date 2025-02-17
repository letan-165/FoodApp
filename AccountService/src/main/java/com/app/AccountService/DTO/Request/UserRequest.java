package com.app.AccountService.DTO.Request;

import com.app.AccountService.Entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    String name;
    String password;
    String phone;
    String gmail;
    String role;
}
