package com.app.OrderService.DTOMock;

import com.app.OrderService.DTO.Request.Restaurant.ItemEditRequest;
import com.app.OrderService.DTO.Request.Restaurant.RestaurantSaveRequest;
import com.app.OrderService.DTO.Request.Restaurant.RestaurantUpdateRequest;
import com.app.OrderService.Enum.RestaurantStatus;

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
}
