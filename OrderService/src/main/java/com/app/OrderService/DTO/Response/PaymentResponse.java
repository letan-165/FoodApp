package com.app.OrderService.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {
    @Id
    Long paymentID;
    Long orderID;
    Long amount;
    LocalDateTime time;
    String method;
    String status;
}
