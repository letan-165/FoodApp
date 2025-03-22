package com.app.OrderService.Mapper.CustomMapper;

import com.app.OrderService.DTO.BaseDTO.ItemCartEntity;
import com.app.OrderService.DTO.BaseDTO.ItemEntity;
import com.app.OrderService.DTO.BaseDTO.RestaurantCartEntity;
import com.app.OrderService.DTO.Response.Cart.RestaurantCartResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CustomMapper {
    @Named("listToMapMenu")
    default Map<Long, ItemEntity> listToMapMenu(List<ItemEntity> menu){
        return menu.stream().collect(Collectors.toMap(ItemEntity::getItemID, item->item));
    }


    @Named("mapToListMenu")
    default List<ItemEntity> mapToListMenu(Map<Long, ItemEntity> menu){
        return menu.values().stream().toList();
    }

    @Named("listToMapItemCart")
    default Map<Long , ItemCartEntity> listToMapItemCart(List<ItemCartEntity> menu){
        return menu.stream().collect(Collectors.toMap(ItemCartEntity::getItemID, item->item));
    }

    @Named("mapToListItemCart")
    default List<ItemCartEntity> mapToListItemCart(Map<Long , ItemCartEntity> menu){
        return menu.values().stream().toList();
    }

    @Named("listToMapRestaurant")
    default Map<String, RestaurantCartEntity> listToMapRestaurants(List<RestaurantCartResponse> restaurants){
        return restaurants.stream().collect(Collectors.toMap(RestaurantCartResponse::getRestaurantID,
                cartItemResponse -> RestaurantCartEntity.builder()
                        .restaurantID(cartItemResponse.getRestaurantID())
                        .menu(listToMapItemCart(cartItemResponse.getMenu()))
                        .build()));
    }

    @Named("mapToListRestaurant")
    default List<RestaurantCartResponse> mapToListRestaurants(Map<String, RestaurantCartEntity> restaurants){
        return  restaurants.values().stream().map(restaurantCartDTO -> RestaurantCartResponse.builder()
                .restaurantID(restaurantCartDTO.getRestaurantID())
                .menu( mapToListItemCart(restaurantCartDTO.getMenu()))
                .build()).toList();
    }
}
