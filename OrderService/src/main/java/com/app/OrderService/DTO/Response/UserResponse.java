package com.app.OrderService.DTO.Response;

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
