package com.app.OrderService.Service;

import com.app.OrderService.DTO.BaseDTO.ItemEntity;
import com.app.OrderService.DTOMock.EntityMock;
import com.app.OrderService.Entity.Restaurant;
import com.app.OrderService.Exception.AppException;
import com.app.OrderService.Exception.ErrorCode;
import com.app.OrderService.Repository.RestaurantRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemServiceTest {
    @InjectMocks ItemService itemService;

    @Mock RestaurantRepository restaurantRepository;

    Restaurant restaurant = EntityMock.restaurantMock();

    @Test
    void findItem_success(){
        when(restaurantRepository.findById(restaurant.getRestaurantID()))
                .thenReturn(Optional.ofNullable(restaurant));


        ItemEntity item = itemService.findItem(restaurant.getRestaurantID(), (long) restaurant.getMenu().size());
        assertThat(item).isEqualTo(restaurant.getMenu().get(2L));
    }

    @Test
    void findItem_fail_noFindRestaurant(){
        when(restaurantRepository.findById(restaurant.getRestaurantID()))
                .thenReturn(Optional.empty());

        var exception = assertThrows(AppException.class,
                () -> itemService.findItem(restaurant.getRestaurantID(),2L));

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.RESTAURANT_NO_EXISTS);
    }

    @Test
    void findItem_fail_noFindItem(){
        when(restaurantRepository.findById(restaurant.getRestaurantID()))
                .thenReturn(Optional.ofNullable(restaurant));

        var exception = assertThrows(AppException.class,
                () -> itemService.findItem(restaurant.getRestaurantID(),3L));

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.ITEM_NO_EXISTS);
    }



}
