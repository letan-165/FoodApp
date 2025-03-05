package com.app.OrderService.Mapper;

import com.app.OrderService.DTO.Response.CartResponse;
import com.app.OrderService.DTO.Response.RestaurantResponse;
import com.app.OrderService.Entity.Cart;
import com.app.OrderService.Entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring", uses = {RestaurantMapper.class})
public abstract class CartMapper {

    @Autowired
    protected RestaurantMapper restaurantMapper;

    @Mapping(target = "restaurants", source = "restaurants", qualifiedByName = "mapToList")
    public abstract CartResponse toCartResponse(Cart cart);

    @Named("mapToList")
    public List<RestaurantResponse> mapToList(Map<String, Restaurant> restaurants) {
        return restaurants.values().stream()
                .map(restaurantMapper::toRestaurantResponse)
                .toList();
    }
}
