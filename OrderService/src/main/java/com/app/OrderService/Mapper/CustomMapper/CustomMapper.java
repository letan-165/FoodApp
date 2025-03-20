package com.app.OrderService.Mapper.CustomMapper;

import com.app.OrderService.DTO.BaseDTO.ItemDTO;
import com.app.OrderService.Entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CustomMapper {
    @Named("listToMapMenu")
    default Map<Long, ItemDTO> listToMapMenu(List<ItemDTO> menu){
        return menu.stream().collect(Collectors.toMap(ItemDTO::getItemID,item->item));
    }
    @Named("mapToListMenu")
    default List<ItemDTO> mapToListMenu(Map<Long, ItemDTO> menu){
        return menu.values().stream().toList();
    }



    @Named("listToMapRestaurant")
    default Map<String, Restaurant> listToMapRestaurants(List<Restaurant> restaurants){
        return restaurants.stream().collect(Collectors.toMap(Restaurant::getRestaurantID,restaurant->restaurant));
    }
    @Named("mapToListRestaurant")
    default List<Restaurant> mapToListRestaurants(Map<Long, Restaurant> restaurants){
        return restaurants.values().stream().toList();
    }
}
