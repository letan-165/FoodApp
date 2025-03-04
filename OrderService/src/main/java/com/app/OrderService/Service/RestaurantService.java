package com.app.OrderService.Service;

import com.app.OrderService.DTO.Request.RestaurantRequest;
import com.app.OrderService.DTO.Response.RestaurantResponse;
import com.app.OrderService.DTO.Response.UserResponse;
import com.app.OrderService.Entity.Restaurant;
import com.app.OrderService.Enum.RestaurantStatus;
import com.app.OrderService.Exception.AppException;
import com.app.OrderService.Exception.ErrorCode;
import com.app.OrderService.Mapper.RestaurantMapper;
import com.app.OrderService.Model.ItemModel;
import com.app.OrderService.Repository.HttpClient.UserClient;
import com.app.OrderService.Repository.RestaurantRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class RestaurantService {
    RestaurantRepository restaurantRepository;
    RestaurantMapper restaurantMapper;
    UserClient userClient;

    public List<RestaurantResponse> findAll(){
        var restaurants = restaurantRepository.findAll();
        return restaurants.stream().map(restaurantMapper::toRestaurantResponse)
                .toList();
    }

    public RestaurantResponse save(RestaurantRequest request){
        Restaurant restaurant = restaurantMapper.toRestaurant(request);
        UserResponse user = userClient.findById(request.getUserID()).getResult();
        restaurant.setStatus(RestaurantStatus.PENDING);

        return restaurantMapper.toRestaurantResponse(restaurantRepository.save(restaurant));
    }

    public RestaurantResponse findById(String restaurantID){
        Restaurant restaurant =  restaurantRepository.findById(restaurantID)
                .orElseThrow(()->new AppException(ErrorCode.RESTAURANT_NO_EXISTS));

        return restaurantMapper.toRestaurantResponse(restaurant);
    }

    public RestaurantResponse update(String restaurantID, RestaurantRequest request) {
        UserResponse user = userClient.findById(request.getUserID()).getResult();

        Restaurant restaurant = restaurantRepository.findById(restaurantID)
                .orElseThrow(()-> new AppException(ErrorCode.RESTAURANT_NO_EXISTS));

        Restaurant restaurantRequest = restaurantMapper.toRestaurant(request);
        restaurantRequest.setRestaurantID(restaurantID);
        restaurantRequest.setMenu(restaurant.getMenu());

        return restaurantMapper.toRestaurantResponse(
                restaurantRepository.save(restaurant));
    }

    public Boolean delete(String restaurantID){
        try {
            restaurantRepository.deleteById(restaurantID);
            return true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.RESTAURANT_NO_EXISTS);
        }
    }

    public ItemModel addItem(String restaurantID, ItemModel request) {
        Restaurant restaurant = restaurantRepository.findById(restaurantID)
                .orElseThrow(()->new AppException(ErrorCode.RESTAURANT_NO_EXISTS));

        long itemID = restaurant.getMenu().keySet().stream()
                .max(Comparator.naturalOrder()).orElse(0L);

        request.setItemID(itemID + 1);
        restaurant.getMenu().putIfAbsent(itemID + 1,request);
        restaurantRepository.save(restaurant);

        return request;
    }

    public ItemModel editItem(String restaurantID,Long itemID , ItemModel request) {
        Restaurant restaurant = restaurantRepository.findById(restaurantID)
                .orElseThrow(()->new AppException(ErrorCode.RESTAURANT_NO_EXISTS));
        if(restaurant.getMenu().get(itemID)==null){
            throw new AppException(ErrorCode.ITEM_NO_EXISTS);
        }
        request.setItemID(itemID);
        restaurant.getMenu().put(itemID,request);
        restaurantRepository.save(restaurant);

        return request;
    }





    public Boolean deleteItem(String restaurantID,Long itemID) {
        Restaurant restaurant = restaurantRepository.findById(restaurantID)
                .orElseThrow(()->new AppException(ErrorCode.RESTAURANT_NO_EXISTS));

        if(restaurant.getMenu().remove(itemID)==null){
            throw new AppException(ErrorCode.ITEM_NO_EXISTS);
        }

        restaurantRepository.save(restaurant);
        return true;
    }

    public Boolean deleteMenu(String restaurantID) {
        Restaurant restaurant = restaurantRepository.findById(restaurantID)
                .orElseThrow(()->new AppException(ErrorCode.RESTAURANT_NO_EXISTS));
        if(restaurant.getMenu().isEmpty()){
            throw new AppException(ErrorCode.ITEM_NO_EXISTS);
        }

        restaurant.getMenu().clear();
        restaurantRepository.save(restaurant);
        return restaurant.getMenu().isEmpty();
    }


    
}
