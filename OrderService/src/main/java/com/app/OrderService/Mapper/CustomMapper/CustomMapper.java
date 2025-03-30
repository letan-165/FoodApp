package com.app.OrderService.Mapper.CustomMapper;

import com.app.OrderService.DTO.BaseDTO.ItemCartEntity;
import com.app.OrderService.DTO.BaseDTO.ItemEntity;
import com.app.OrderService.DTO.BaseDTO.RestaurantCartEntity;
import com.app.OrderService.DTO.Response.Cart.RestaurantCartResponse;
import com.app.OrderService.Entity.Restaurant;
import com.app.OrderService.Exception.AppException;
import com.app.OrderService.Exception.ErrorCode;
import com.app.OrderService.Repository.RestaurantRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CustomMapper {
    @Named("listToMapMenu")
    public Map<Long, ItemEntity> listToMapMenu(List<ItemEntity> menu){
        return menu.stream().collect(Collectors.toMap(ItemEntity::getItemID, item->item));
    }


    @Named("mapToListMenu")
    public List<ItemEntity> mapToListMenu(Map<Long, ItemEntity> menu){
        return menu.values().stream().toList();
    }


    @Autowired
    private RestaurantRepository restaurantRepository;

    @Named("mapToListItemCart")
    public List<ItemEntity> mapToListItemCart(Map<Long , ItemCartEntity> menu,@Context String restaurantID){
        Restaurant restaurant = restaurantRepository.findById(restaurantID)
                .orElseThrow(()->new AppException(ErrorCode.RESTAURANT_NO_EXISTS));

        return menu.values().stream().map(item->{
            ItemEntity item1 = restaurant.getMenu().get(item.getItemID());
            item1 = ItemEntity.builder()
                    .itemID(item.getItemID())
                    .name(item1!=null ? item1.getName() : "Món ăn ngừng sản xuất")
                    .price(item1!=null ? item1.getPrice() : 0L)
                    .quantity(item.getQuantity())
                    .build();
            return item1;
        }).toList();
    }

    @Named("mapToListRestaurant")
    public List<RestaurantCartResponse> mapToListRestaurants(Map<String, RestaurantCartEntity> restaurants){
        return  restaurants.values().stream().map(restaurantCartDTO -> {
            Restaurant restaurant = restaurantRepository.findById(restaurantCartDTO.getRestaurantID())
                    .orElseThrow(()->new AppException(ErrorCode.RESTAURANT_NO_EXISTS));

            return RestaurantCartResponse.builder()
                .restaurantID(restaurantCartDTO.getRestaurantID())
                .name(restaurant.getName())
                .menu( mapToListItemCart(restaurantCartDTO.getMenu(),restaurant.getRestaurantID()))
                .build();

        }).toList();
    }
}
