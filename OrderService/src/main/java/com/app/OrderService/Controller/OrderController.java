package com.app.OrderService.Controller;

import com.app.OrderService.DTO.ApiResponse;
import com.app.OrderService.DTO.Request.Order.OrderSaveRequest;
import com.app.OrderService.DTO.Request.Order.OrderUpdateReQuest;
import com.app.OrderService.DTO.Response.Cart.CartResponse;
import com.app.OrderService.DTO.Response.OrderResponse;
import com.app.OrderService.Service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class OrderController {
    OrderService orderService;

    @GetMapping
    ApiResponse<List<OrderResponse>> findAll(){
        return ApiResponse.<List<OrderResponse>>builder()
                .message("Lấy danh sách đơn hàng")
                .result(orderService.findAll())
                .build();
    }

    @PostMapping
    ApiResponse<OrderResponse> save(@RequestBody OrderSaveRequest request){
        return ApiResponse.<OrderResponse>builder()
                .message("Thêm đơn hàng")
                .result(orderService.save(request,false))
                .build();
    }
    @PostMapping("/cart")
    ApiResponse<OrderResponse> saveFromCart(@RequestBody OrderSaveRequest request){
        return ApiResponse.<OrderResponse>builder()
                .message("Thêm đơn hàng từ giỏ hàng")
                .result(orderService.save(request,true))
                .build();
    }


    @PutMapping("/{orderID}")
    ApiResponse<OrderResponse> update(@PathVariable String orderID,@RequestBody OrderUpdateReQuest request){
        return ApiResponse.<OrderResponse>builder()
                .message("Cập nhật đơn hàng")
                .result(orderService.update(orderID,request))
                .build();
    }

    @GetMapping("/{orderID}")
    ApiResponse<OrderResponse> findByID(@PathVariable String orderID){
        return ApiResponse.<OrderResponse>builder()
                .message("Lấy đơn hàng: "+orderID)
                .result(orderService.findById(orderID))
                .build();
    }

    @DeleteMapping("/{orderID}")
    ApiResponse<Boolean> delete(@PathVariable String orderID){
        return ApiResponse.<Boolean>builder()
                .message("Xóa đơn hàng: "+orderID)
                .result(orderService.delete(orderID))
                .build();
    }



}
