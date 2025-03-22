package com.app.OrderService.Controller;

import com.app.OrderService.DTO.ApiResponse;
import com.app.OrderService.DTO.Request.Cart.ItemCartRequest;
import com.app.OrderService.DTO.Response.Cart.CartResponse;
import com.app.OrderService.DTO.Response.Cart.RestaurantCartResponse;
import com.app.OrderService.Service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class CartController {
    CartService cartService;

    @GetMapping
    ApiResponse<List<CartResponse>> findAll(){
        return ApiResponse.<List<CartResponse>>builder()
                .message("Lấy danh sách giỏ hàng")
                .result(cartService.findAll())
                .build();
    }

    @GetMapping("/{cartID}")
    ApiResponse<CartResponse> findByID(@PathVariable String cartID){
        return ApiResponse.<CartResponse>builder()
                .message("Lấy  giỏ hàng: "+cartID)
                .result(cartService.findById(cartID))
                .build();
    }

    @PostMapping("/{cartID}")
    ApiResponse<CartResponse> save(@PathVariable String cartID){
        return ApiResponse.<CartResponse>builder()
                .message("Thêm mới giỏ hàng: "+cartID)
                .result(cartService.save(cartID))
                .build();
    }

    @DeleteMapping("/{cartID}")
    ApiResponse<Boolean> delete(@PathVariable String cartID){
        return ApiResponse.<Boolean>builder()
                .message("Xóa giỏ hàng: "+cartID)
                .result(cartService.delete(cartID))
                .build();
    }

    @PutMapping("/item/add")
    ApiResponse<RestaurantCartResponse> addItem(@RequestBody ItemCartRequest request){
        return ApiResponse.<RestaurantCartResponse>builder()
                .message("Thêm món ăn : "+request.getItemID())
                .result(cartService.addItem(request))
                .build();
    }

    @PutMapping("/item/delete")
    ApiResponse<Boolean> deleteItem(@RequestBody ItemCartRequest request){
        return ApiResponse.<Boolean>builder()
                .message("Xóa món ăn : "+request.getItemID())
                .result(cartService.deleteItem(request))
                .build();
    }
}
