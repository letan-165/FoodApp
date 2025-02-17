package com.app.AccountService.DTO.Response;

import com.app.AccountService.Entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String userID;
    String name;
    String phone;
    String gmail;
    String role;
}
