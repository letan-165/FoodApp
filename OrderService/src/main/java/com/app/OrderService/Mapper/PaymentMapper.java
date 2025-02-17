package com.app.OrderService.Mapper;

import com.app.OrderService.DTO.Request.PaymentRequest;
import com.app.OrderService.DTO.Response.PaymentResponse;
import com.app.OrderService.Entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toPayment(PaymentRequest request);
    PaymentResponse toPaymentResponse(Payment payment);
}
