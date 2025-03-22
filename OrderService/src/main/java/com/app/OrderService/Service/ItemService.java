package com.app.OrderService.Service;

import com.app.OrderService.DTO.BaseDTO.ItemEntity;
import com.app.OrderService.Entity.Restaurant;
import com.app.OrderService.Exception.AppException;
import com.app.OrderService.Exception.ErrorCode;
import com.app.OrderService.Repository.RestaurantRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class ItemService {
    RestaurantRepository restaurantRepository;

    public ItemEntity findItem(String restaurantID, Long itemID){
        Restaurant restaurant = restaurantRepository.findById(restaurantID)
                .orElseThrow(()->new AppException(ErrorCode.RESTAURANT_NO_EXISTS));

        ItemEntity itemDTO = restaurant.getMenu().get(itemID);

        if(itemDTO == null)
            throw new AppException(ErrorCode.ITEM_NO_EXISTS);

        return itemDTO;
    }
}
