package com.app.OrderService.Mapper;

import com.app.OrderService.DTO.Request.Restaurant.RestaurantSaveRequest;
import com.app.OrderService.DTO.Request.Restaurant.RestaurantUpdateRequest;
import com.app.OrderService.DTO.Response.Restaurant.RestaurantResponse;
import com.app.OrderService.Entity.Restaurant;
import com.app.OrderService.Mapper.CustomMapper.CustomMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CustomMapper.class)
public interface RestaurantMapper {
    @Mapping(target = "menu", source = "menu", qualifiedByName = "mapToListMenu")
    RestaurantResponse toRestaurantResponse(Restaurant restaurant);


    Restaurant toRestaurant(RestaurantSaveRequest request);
    Restaurant toRestaurant(RestaurantUpdateRequest request);

}
