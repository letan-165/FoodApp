package com.app.OrderService.Service;

import com.app.OrderService.DTO.Request.RestaurantRequest;
import com.app.OrderService.DTO.Response.RestaurantResponse;
import com.app.OrderService.Entity.Restaurant;
import com.app.OrderService.Exception.AppException;
import com.app.OrderService.Exception.ErrorCode;
import com.app.OrderService.Mapper.RestaurantMapper;
import com.app.OrderService.Repository.RestaurantRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class RestaurantService {
    RestaurantRepository restaurantRepository;
    RestaurantMapper restaurantMapper;

    public List<Restaurant> findAll(){
        return restaurantRepository.findAll();
    }

    public RestaurantResponse save(RestaurantRequest request){
        //check userID
        return restaurantMapper.toRestaurantResponse(
                restaurantRepository.save(restaurantMapper.toRestaurant(request)));
    }

    public RestaurantResponse findById(String restaurantID){
        //check userID
        return restaurantMapper.toRestaurantResponse(
                restaurantRepository.findById(restaurantID)
                        .orElseThrow(()->new AppException(ErrorCode.RESTAURANT_NO_EXISTS)));
    }

    public RestaurantResponse update(String restaurantID, RestaurantRequest request) {
        //check userID
        //check restaurantID
        if (!restaurantRepository.existsById(restaurantID)) {
            throw new AppException(ErrorCode.RESTAURANT_NO_EXISTS);
        }
        //Set ID
        Restaurant restaurant = restaurantMapper.toRestaurant(request);
        restaurant.setRestaurantID(restaurantID);
        return restaurantMapper.toRestaurantResponse(
                restaurantRepository.save(restaurant));
    }

    public Boolean delete(String restaurantID){
        //check userID
        try {
            restaurantRepository.deleteById(restaurantID);
            return true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.RESTAURANT_NO_EXISTS);
        }
    }


    
}
