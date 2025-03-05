package com.app.OrderService.Controller;

import com.app.OrderService.DTO.ApiResponse;
import com.app.OrderService.DTO.Request.CartRequest;
import com.app.OrderService.DTO.Response.CartResponse;
import com.app.OrderService.Entity.Cart;
import com.app.OrderService.Model.ItemModel;
import com.app.OrderService.Service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/cart"))
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class CartController {
    CartService cartService;

    @GetMapping
    public ApiResponse<List<CartResponse>> findAll(){
        return ApiResponse.<List<CartResponse>>builder()
                .result(cartService.findAll())
                .build();
    }

    @PostMapping
    public ApiResponse<CartResponse> save(@RequestBody CartRequest request){
        return ApiResponse.<CartResponse>builder()
                .result(cartService.save(request.getUserID()))
                .build();
    }

    @PostMapping("/{cartID}")
    public ApiResponse<CartResponse> findById(@PathVariable String cartID){
        return ApiResponse.<CartResponse>builder()
                .result(cartService.findById(cartID))
                .build();
    }

    @DeleteMapping("/{cartID}")
    public ApiResponse<Boolean> delete(@PathVariable String cartID){
        return ApiResponse.<Boolean>builder()
                .result(cartService.delete(cartID))
                .build();
    }

    @PutMapping("/id={cartID}/rid={restaurantID}/add")
    public ApiResponse<ItemModel> addItem(@PathVariable String cartID,@PathVariable String restaurantID,@RequestBody ItemModel request){
        return ApiResponse.<ItemModel>builder()
                .result(cartService.addItem( cartID, restaurantID, request))
                .build();


    }@PutMapping("/id={cartID}/rid={restaurantID}/mid={itemID}/delete")
    public ApiResponse<Boolean> deleteID(@PathVariable String cartID,@PathVariable String restaurantID,@PathVariable Long itemID){
        return ApiResponse.<Boolean>builder()
                .result(cartService.deleteID( cartID, restaurantID, itemID))
                .build();
    }


}
