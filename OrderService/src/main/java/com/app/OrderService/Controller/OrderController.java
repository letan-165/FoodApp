package com.app.OrderService.Controller;

import com.app.OrderService.DTO.ApiResponse;
import com.app.OrderService.DTO.Request.Order.OrderSaveRequest;
import com.app.OrderService.DTO.Request.Order.OrderUpdateReQuest;
import com.app.OrderService.DTO.Request.Order.OrderUpdateStatusRequest;
import com.app.OrderService.DTO.Response.OrderResponse;
import com.app.OrderService.Service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
    @GetMapping("/user/{userID}")
    ApiResponse<List<OrderResponse>> findAllByUser(@PathVariable String userID){
        return ApiResponse.<List<OrderResponse>>builder()
                .message("Lấy danh sách đơn hàng của user " + userID)
                .result(orderService.findAllByUser(userID))
                .build();
    }
    @GetMapping("/restaurant/{restaurantID}")
    ApiResponse<List<OrderResponse>> findAllByRestaurant(@PathVariable String restaurantID){
        return ApiResponse.<List<OrderResponse>>builder()
                .message("Lấy danh sách đơn hàng của restaurant " + restaurantID)
                .result(orderService.findAllByRestaurant(restaurantID))
                .build();
    }

    @GetMapping("/shipper/{shipperID}")
    ApiResponse<List<OrderResponse>> findAllByShipper(@PathVariable String shipperID){
        return ApiResponse.<List<OrderResponse>>builder()
                .message("Lấy danh sách đơn hàng của shipper " + shipperID)
                .result(orderService.findAllByShipper(shipperID))
                .build();
    }
    @GetMapping("/status/{status}")
    ApiResponse<List<OrderResponse>> findAllStatus(@PathVariable String status){
        return ApiResponse.<List<OrderResponse>>builder()
                .message("Lấy danh sách đơn hàng có trạng thái " + status)
                .result(orderService.findAllStatus(status))
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

    @PutMapping("/status/{orderID}")
    ApiResponse<OrderResponse> updateStatus(@PathVariable String orderID,@RequestBody OrderUpdateStatusRequest request){
        return ApiResponse.<OrderResponse>builder()
                .message("Cập nhật trạng thái đơn hàng")
                .result(orderService.updateStatus(orderID,request))
                .build();
    }



}
