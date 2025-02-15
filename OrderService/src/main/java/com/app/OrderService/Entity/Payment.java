package com.app.OrderService.Entity;

import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {
    @Id
    Long paymentID;
    Long orderID;
    Long amount;
    LocalDateTime time;
    String method;
    String status;
}
