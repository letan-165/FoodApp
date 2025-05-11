package com.app.OrderService.DTOMock;

import com.app.OrderService.DTO.Request.Cart.ItemCartRequest;
import com.app.OrderService.DTO.Request.Order.OrderSaveRequest;
import com.app.OrderService.DTO.Request.Restaurant.ItemEditRequest;
import com.app.OrderService.DTO.Request.Restaurant.RestaurantSaveRequest;
import com.app.OrderService.DTO.Request.Restaurant.RestaurantUpdateRequest;
import com.app.OrderService.Enum.RestaurantStatus;

import java.util.List;

public class RequestMock {
    public static RestaurantSaveRequest restaurantSaveRequest(){
        return RestaurantSaveRequest.builder()
                .userID("userID")
                .name("name")
                .address("address")
                .phone("phone")
                .build();
    }

    public static RestaurantUpdateRequest restaurantUpdateRequest(){
        return RestaurantUpdateRequest.builder()
                .userID("userID")
                .name("name")
                .address("address")
                .phone("phone")
                .status(RestaurantStatus.PENDING)
                .build();
    }

    public static ItemEditRequest itemEditRequest(){
        return ItemEditRequest.builder()
                .name("name")
                .quantity(10L)
                .price(10000L)
                .build();
    }

    public static ItemCartRequest itemCartRequest(Long id){
        return ItemCartRequest.builder()
                .cartID("cartID")
                .restaurantID("restaurantID")
                .itemID(id)
                .build();
    }

    public static OrderSaveRequest orderSaveRequest(){
        return OrderSaveRequest.builder()
                .customerID("customerID")
                .restaurantID("restaurantID")
                .paymentID("paymentID")
                .menu(List.of(EntityMock.itemCartEntity(1L),EntityMock.itemCartEntity(2L)))
                .address("address")
                .phone("phone")
                .build();
    }


}
