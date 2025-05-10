package com.app.OrderService.Service;

import com.app.OrderService.DTO.Request.Payment.PaymentRequest;
import com.app.OrderService.DTO.Response.PaymentResponse;
import com.app.OrderService.Entity.Payment;
import com.app.OrderService.Exception.AppException;
import com.app.OrderService.Exception.ErrorCode;
import com.app.OrderService.Mapper.PaymentMapper;
import com.app.OrderService.Repository.OrderRepository;
import com.app.OrderService.Repository.PaymentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class PaymentService {
    PaymentRepository paymentRepository;
    OrderRepository orderRepository;
    PaymentMapper paymentMapper;


    public List<Payment> findAll(){
        return paymentRepository.findAll();
    }

    public PaymentResponse save(PaymentRequest request){
        orderRepository.findById(request.getOrderID())
                .orElseThrow(()->new AppException(ErrorCode.ORDER_NO_EXISTS));
        return paymentMapper.toPaymentResponse(
                paymentRepository.save(
                        paymentMapper.toPayment(request)));
    }

    public PaymentResponse findById(String paymentID){
        return paymentMapper.toPaymentResponse(
                paymentRepository.findById(paymentID)
                        .orElseThrow(()->new AppException(ErrorCode.PAYMENT_NO_EXISTS)));
    }

    public PaymentResponse update(String paymentID, PaymentRequest request) {
        orderRepository.findById(request.getOrderID())
                .orElseThrow(()->new AppException(ErrorCode.ORDER_NO_EXISTS));
        if (!paymentRepository.existsById(paymentID)) {
            throw new AppException(ErrorCode.PAYMENT_NO_EXISTS);
        }

        Payment payment = paymentMapper.toPayment(request);
        payment.setPaymentID(paymentID);
        return paymentMapper.toPaymentResponse(
                paymentRepository.save(payment));
    }

    public Boolean delete(String paymentID){
        try {
            paymentRepository.deleteById(paymentID);
            return true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.PAYMENT_NO_EXISTS);
        }
    }



}