package com.app.OrderService.Mapper;

import com.app.OrderService.DTO.BaseDTO.RestaurantCartEntity;
import com.app.OrderService.DTO.Response.Cart.CartResponse;
import com.app.OrderService.DTO.Response.Cart.RestaurantCartResponse;
import com.app.OrderService.Entity.Cart;
import com.app.OrderService.Mapper.CustomMapper.CustomMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = CustomMapper.class)
public interface CartMapper {
    @Mapping(target = "restaurants",source = "restaurants",qualifiedByName = "mapToListRestaurant")
    CartResponse toCartResponse(Cart cart);

    @Mapping(target = "menu",source = "menu",qualifiedByName = "mapToListItemCart")
    RestaurantCartResponse toRestaurantCartResponse(RestaurantCartEntity entity,@Context String restaurantID);



}
