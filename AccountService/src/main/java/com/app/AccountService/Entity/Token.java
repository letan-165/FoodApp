package com.app.AccountService.Entity;

import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token {
    @Id
    String tokenID;
    Instant expiryTime;
}
