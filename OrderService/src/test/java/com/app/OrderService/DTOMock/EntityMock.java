package com.app.OrderService.DTOMock;

import com.app.OrderService.DTO.BaseDTO.ItemEntity;
import com.app.OrderService.Entity.Restaurant;
import com.app.OrderService.Enum.RestaurantStatus;

import java.util.ArrayList;
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
        Map<Long, ItemEntity> itemEntityMap = itemEntities.stream()
                .collect(Collectors.toMap( item -> atomicLong.getAndIncrement(),item->item));

        return Restaurant.builder()
                .restaurantID("restaurantID")
                .userID("userID")
                .name("name")
                .menu(itemEntityMap)
                .address("address")
                .phone("phone")
                .status(RestaurantStatus.PENDING)
                .build();
    }


}
