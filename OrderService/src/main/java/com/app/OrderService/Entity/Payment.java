package com.app.OrderService.Entity;

import com.app.OrderService.Enum.PaymentMethod;
import com.app.OrderService.Enum.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {
    @Id
    String paymentID;
    String orderID;
    Long amount;
    Instant time;
    PaymentMethod method;
    PaymentStatus status;
}
