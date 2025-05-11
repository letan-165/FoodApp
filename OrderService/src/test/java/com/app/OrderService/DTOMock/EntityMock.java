package com.app.OrderService.DTOMock;

import com.app.OrderService.DTO.BaseDTO.ItemCartEntity;
import com.app.OrderService.DTO.BaseDTO.ItemEntity;
import com.app.OrderService.DTO.BaseDTO.RestaurantCartEntity;
import com.app.OrderService.Entity.Cart;
import com.app.OrderService.Entity.Restaurant;
import com.app.OrderService.Enum.RestaurantStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class EntityMock {
    public static ItemEntity itemEntityMock(Long id){
        return ItemEntity.builder()
                .itemID(id)
                .name("name")
                .price(10000L)
                .quantity(10L)
                .build();
    }

    public static Restaurant restaurantMock(){
        AtomicLong atomicLong = new AtomicLong(1);
        List<ItemEntity> itemEntities = new ArrayList<>(List.of(itemEntityMock(1L),itemEntityMock(2L)));
        Map<Long, ItemEntity> menu = itemEntities.stream()
                .collect(Collectors.toMap( item -> atomicLong.getAndIncrement(),item->item));

        return Restaurant.builder()
                .restaurantID("restaurantID")
                .userID("userID")
                .name("name")
                .menu(menu)
                .address("address")
                .phone("phone")
                .status(RestaurantStatus.PENDING)
                .build();
    }

    public static ItemCartEntity itemCartEntity(Long id){
        return ItemCartEntity.builder()
                .itemID(id)
                .quantity(1L)
                .build();
    }

    public static RestaurantCartEntity restaurantCartEntity(){
        AtomicLong atomicLong = new AtomicLong(1);
        List<ItemCartEntity> itemCartEntities = new ArrayList<>(List.of(itemEntityMock(1L),itemEntityMock(2L)));
        Map<Long, ItemCartEntity> menu = itemCartEntities.stream()
                .collect(Collectors.toMap( item -> atomicLong.getAndIncrement(),item->item));

        return RestaurantCartEntity.builder()
                .restaurantID("restaurantID")
                .menu(menu)
                .build();
    }

    public static Cart cart(){
        return Cart.builder()
                .userID("userID")
                .restaurants(new HashMap<>(Map.of("restaurantID", restaurantCartEntity())))
                .build();
    }


}
