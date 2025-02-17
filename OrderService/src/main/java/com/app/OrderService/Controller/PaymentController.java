package com.app.OrderService.Controller;

import com.app.OrderService.DTO.ApiResponse;
import com.app.OrderService.DTO.Request.PaymentRequest;
import com.app.OrderService.DTO.Response.PaymentResponse;
import com.app.OrderService.Entity.Payment;
import com.app.OrderService.Service.PaymentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PaymentController {
    PaymentService paymentService;

    @GetMapping
    ApiResponse<List<Payment>>findAll(){
        return ApiResponse.<List<Payment>>builder()
                .result(paymentService.findAll())
                .build();
    }

    @PostMapping
    ApiResponse<PaymentResponse>save(@RequestBody PaymentRequest request){
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.save(request))
                .build();
    }

    @PostMapping("/{paymentID}")
    ApiResponse<PaymentResponse>findById(@PathVariable String paymentID){
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.findById(paymentID))
                .build();
    }
    @PutMapping("/{paymentID}")
    ApiResponse<PaymentResponse>update( @PathVariable String paymentID,@RequestBody PaymentRequest request){
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.update(paymentID,request))
                .build();
    }
    @DeleteMapping("/{paymentID}")
    ApiResponse<Boolean>delete(@PathVariable String paymentID){
        return ApiResponse.<Boolean>builder()
                .result(paymentService.delete(paymentID))
                .build();
    }


}
