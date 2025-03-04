package com.app.OrderService.Mapper;

import com.app.OrderService.DTO.Request.RestaurantRequest;
import com.app.OrderService.DTO.Response.RestaurantResponse;
import com.app.OrderService.Entity.Restaurant;
import com.app.OrderService.Model.ItemModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring",uses = CustomerMapper.class)
public interface RestaurantMapper {
    Restaurant toRestaurant(RestaurantRequest request);


    @Mapping(target = "menu", source = "menu", qualifiedByName = "mapToList")
    RestaurantResponse toRestaurantResponse(Restaurant restaurant);
}
