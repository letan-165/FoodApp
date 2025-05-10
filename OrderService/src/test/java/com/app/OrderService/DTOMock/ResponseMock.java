package com.app.OrderService.DTOMock;

import com.app.OrderService.DTO.Response.Restaurant.ItemRestaurantResponse;
import com.app.OrderService.DTO.Response.Restaurant.RestaurantResponse;
import com.app.OrderService.Enum.RestaurantStatus;

public class ResponseMock {
    public static RestaurantResponse restaurantResponse (){
        return RestaurantResponse.builder()
                .restaurantID("restaurantID")
                .userID("userID")
                .name("name")
                .menu(EntityMock.restaurantMock().getMenu().values().stream().toList())
                .address("address")
                .phone("phone")
                .status(RestaurantStatus.PENDING)
                .build();
    }

    public static ItemRestaurantResponse itemRestaurantResponse(String name) {
        return ItemRestaurantResponse.builder()
                .name(name)
                .price(10000L)
                .build();
    }
}
