package com.app.AccountService.DTO.Request;

import com.app.AccountService.Entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

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

    @Builder.Default
    List<String> roles = new ArrayList<>(Collections.emptyList());;
}
