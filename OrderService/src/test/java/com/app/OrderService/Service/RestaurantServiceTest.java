package com.app.OrderService.Service;

import com.app.OrderService.DTO.ApiResponse;
import com.app.OrderService.DTO.Request.Restaurant.ItemEditRequest;
import com.app.OrderService.DTO.Request.Restaurant.RestaurantSaveRequest;
import com.app.OrderService.DTO.Request.Restaurant.RestaurantUpdateRequest;
import com.app.OrderService.DTO.Response.HttpClient.UserResponse;
import com.app.OrderService.DTO.Response.Restaurant.ItemRestaurantResponse;
import com.app.OrderService.DTO.Response.Restaurant.RestaurantResponse;
import com.app.OrderService.DTOMock.EntityMock;
import com.app.OrderService.DTOMock.RequestMock;
import com.app.OrderService.DTOMock.ResponseMock;
import com.app.OrderService.Entity.Restaurant;
import com.app.OrderService.Enum.RestaurantStatus;
import com.app.OrderService.Exception.AppException;
import com.app.OrderService.Exception.ErrorCode;
import com.app.OrderService.Mapper.ItemMapper;
import com.app.OrderService.Mapper.RestaurantMapper;
import com.app.OrderService.Repository.HttpClient.UserClient;
import com.app.OrderService.Repository.RestaurantRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
@Slf4j
public class RestaurantServiceTest {
    @InjectMocks RestaurantService restaurantService;
    @Mock
    RestaurantRepository restaurantRepository;
    @Mock
    UserClient userClient;
    @Mock
    RestaurantMapper restaurantMapper;

    Restaurant restaurant;
    RestaurantResponse restaurantResponse;
    RestaurantSaveRequest restaurantSaveRequest;
    RestaurantUpdateRequest restaurantUpdateRequest;
    ItemRestaurantResponse itemRestaurantResponse;
    ItemEditRequest itemEditRequest;
    String userID;
    String restaurantID;

    @BeforeEach
    void initData(){
        restaurant = EntityMock.restaurantMock();
        restaurantResponse = ResponseMock.restaurantResponse();
        restaurantSaveRequest = RequestMock.restaurantSaveRequest();
        restaurantUpdateRequest = RequestMock.restaurantUpdateRequest();
        itemRestaurantResponse = ResponseMock.itemRestaurantResponse("item1");
        itemEditRequest = RequestMock.itemEditRequest();
        userID = "userID";
        restaurantID = restaurantResponse.getRestaurantID();
    }

    @Test
    void findAllByUserID_success(){
        String userID = "userID";
        when(restaurantRepository.findAllByUserID(eq(userID))).thenReturn(List.of(restaurant,restaurant));
        restaurantService.findAllByUserID(userID);

        verify(userClient).findById(eq(userID));
        verify(restaurantRepository).findAllByUserID(eq(userID));
        verify(restaurantMapper,times(2)).toRestaurantResponse(any());
    }

    @Test
    void findAllByUserID_fail_noFindUser(){
        String userID = "userID";
        RuntimeException  exception = new RuntimeException("User no exists");
        when(userClient.findById(userID)).thenThrow(exception);
        var responseException = assertThrows(Exception.class, ()-> restaurantService.findAllByUserID(userID));

        assertThat(responseException).isEqualTo(exception);
    }

    @Test
    void save_success(){
        when(userClient.findById(userID)).thenReturn(new ApiResponse<>());
        when(restaurantMapper.toRestaurant(restaurantSaveRequest)).thenReturn(restaurant);
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);
        when(restaurantMapper.toRestaurantResponse(restaurant)).thenReturn(restaurantResponse);

        RestaurantResponse response = restaurantService.save(restaurantSaveRequest);
        verify(restaurantMapper).toRestaurant(restaurantSaveRequest);

        assertThat(response).isEqualTo(restaurantResponse);
    }

    @Test
    void update_success(){
        when(userClient.findById(userID)).thenReturn(new ApiResponse<>());
        when(restaurantRepository.existsById(restaurantID)).thenReturn(true);
        when(restaurantMapper.toRestaurant(restaurantUpdateRequest)).thenReturn(restaurant);
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);
        when(restaurantMapper.toRestaurantResponse(restaurant)).thenReturn(restaurantResponse);

        RestaurantResponse response = restaurantService.update(restaurantID,restaurantUpdateRequest);


        assertThat(response).isEqualTo(restaurantResponse);
    }

    @Test
    void update_fail_noFindUser(){
        RuntimeException exception = new RuntimeException("User no exist");
        when(userClient.findById(userID)).thenThrow(exception);

        var exceptionResponse = assertThrows(RuntimeException.class,
                ()->restaurantService.update(restaurantID,restaurantUpdateRequest));

        assertThat(exceptionResponse).isEqualTo(exception);

    }

    @Test
    void update_fail_noFindRestaurant(){
        when(userClient.findById(userID)).thenReturn(new ApiResponse<>());
        when(restaurantRepository.existsById(restaurantID)).thenReturn(false);
        var exceptionResponse = assertThrows(AppException.class,
                ()->restaurantService.update(restaurantID,restaurantUpdateRequest));

        assertThat(exceptionResponse.getErrorCode()).isEqualTo(ErrorCode.RESTAURANT_NO_EXISTS);

    }

    @Mock ItemService itemService;
    @Mock ItemMapper itemMapper;

    @Test
    void addItem_success(){
        when(restaurantRepository.findById(restaurantID))
                .thenReturn(Optional.of(restaurant));
        when(itemMapper.toItemRestaurantResponse(itemEditRequest)).thenReturn(itemRestaurantResponse);

        ItemRestaurantResponse response = restaurantService.addItem(restaurantID,itemEditRequest);

        verify(restaurantRepository).save(restaurant);
        assertThat(response).isEqualTo(itemRestaurantResponse);
        assertThat(response.getItemID()).isEqualTo(3L);
    }

    @Test
    void addItem_fail_noFindRestaurant(){
        when(restaurantRepository.findById(restaurantID))
                .thenReturn(Optional.empty());
        var exception = assertThrows(AppException.class,
                ()->restaurantService.addItem(restaurantID,itemEditRequest));

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.RESTAURANT_NO_EXISTS);
    }

    @Test
    void addItem_fail_save(){
        when(restaurantRepository.findById(restaurantID))
                .thenReturn(Optional.of(restaurant));
        when(itemMapper.toItemRestaurantResponse(itemEditRequest)).thenReturn(itemRestaurantResponse);
        when(restaurantRepository.save(restaurant)).thenThrow(new RuntimeException());
        var exception = assertThrows(RuntimeException.class,
                ()-> restaurantService.addItem(restaurantID,itemEditRequest));

        assertThat(exception.getMessage()).isEqualTo("Lưu item không thành công");


    }


}
