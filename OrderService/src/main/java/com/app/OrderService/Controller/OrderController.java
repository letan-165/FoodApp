package com.app.OrderService.Controller;

import com.app.OrderService.DTO.ApiResponse;
import com.app.OrderService.DTO.Request.OrderRequest;
import com.app.OrderService.DTO.Response.OrderResponse;
import com.app.OrderService.Entity.Order;
import com.app.OrderService.Service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class OrderController {
    OrderService orderService;

    @GetMapping
    ApiResponse<List<Order>>findAll(){
        return ApiResponse.<List<Order>>builder()
                .result(orderService.findAll())
                .build();
    }

    @PostMapping
    ApiResponse<OrderResponse>save(@RequestBody OrderRequest request){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.save(request))
                .build();
    }

    @PostMapping("/{orderID}")
    ApiResponse<OrderResponse>findById(@PathVariable String orderID){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.findById(orderID))
                .build();
    }
    @PutMapping("/{orderID}")
    ApiResponse<OrderResponse>update( @PathVariable String orderID,@RequestBody OrderRequest request){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.update(orderID,request))
                .build();
    }
    @DeleteMapping("/{orderID}")
    ApiResponse<Boolean>delete(@PathVariable String orderID){
        return ApiResponse.<Boolean>builder()
                .result(orderService.delete(orderID))
                .build();
    }


}
