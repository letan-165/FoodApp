package com.app.OrderService.Service;

import com.app.OrderService.DTO.Request.PaymentRequest;
import com.app.OrderService.DTO.Response.PaymentResponse;
import com.app.OrderService.Entity.Payment;
import com.app.OrderService.Exception.AppException;
import com.app.OrderService.Exception.ErrorCode;
import com.app.OrderService.Mapper.PaymentMapper;
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
    PaymentMapper paymentMapper;

    public List<Payment> findAll(){
        return paymentRepository.findAll();
    }

    public PaymentResponse save(PaymentRequest request){
        //check orderID
        return paymentMapper.toPaymentResponse(
                paymentRepository.save(
                        paymentMapper.toPayment(request)));
    }

    public PaymentResponse findById(String paymentID){
        //check orderID
        return paymentMapper.toPaymentResponse(
                paymentRepository.findById(paymentID)
                        .orElseThrow(()->new AppException(ErrorCode.PAYMENT_NO_EXISTS)));
    }

    public PaymentResponse update(String paymentID, PaymentRequest request) {
        //check orderID
        //check paymentID
        if (!paymentRepository.existsById(paymentID)) {
            throw new AppException(ErrorCode.PAYMENT_NO_EXISTS);
        }
        //Set ID
        Payment payment = paymentMapper.toPayment(request);
        payment.setPaymentID(paymentID);
        return paymentMapper.toPaymentResponse(
                paymentRepository.save(payment));
    }

    public Boolean delete(String paymentID){
        //check orderID
        try {
            paymentRepository.deleteById(paymentID);
            return true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.PAYMENT_NO_EXISTS);
        }
    }



}
