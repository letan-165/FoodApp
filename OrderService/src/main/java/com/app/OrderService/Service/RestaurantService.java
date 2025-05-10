package com.app.OrderService.Service;

import com.app.OrderService.DTO.BaseDTO.ItemEntity;
import com.app.OrderService.DTO.Request.Restaurant.ItemEditRequest;
import com.app.OrderService.DTO.Request.Restaurant.RestaurantSaveRequest;
import com.app.OrderService.DTO.Request.Restaurant.RestaurantUpdateRequest;
import com.app.OrderService.DTO.Response.Restaurant.ItemRestaurantResponse;
import com.app.OrderService.DTO.Response.Restaurant.RestaurantResponse;
import com.app.OrderService.Entity.Restaurant;
import com.app.OrderService.Enum.RestaurantStatus;
import com.app.OrderService.Exception.AppException;
import com.app.OrderService.Exception.ErrorCode;
import com.app.OrderService.Mapper.ItemMapper;
import com.app.OrderService.Mapper.RestaurantMapper;
import com.app.OrderService.Repository.HttpClient.UserClient;
import com.app.OrderService.Repository.RestaurantRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RestaurantService {
    RestaurantRepository restaurantRepository;
    RestaurantMapper restaurantMapper;
    UserClient userClient;

    public List<RestaurantResponse> findAll(){
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream().map(restaurantMapper::toRestaurantResponse).toList();
    }

    public List<RestaurantResponse> findAllByUserID(String userID){
        userClient.findById(userID);

        List<Restaurant> restaurants = restaurantRepository.findAllByUserID(userID);
        return restaurants.stream().map(restaurantMapper::toRestaurantResponse).toList();
    }

    public RestaurantResponse findById(String restaurantID){
        Restaurant restaurant = restaurantRepository.findById(restaurantID)
                .orElseThrow(()->new AppException(ErrorCode.RESTAURANT_NO_EXISTS));

        return restaurantMapper.toRestaurantResponse
                (restaurantRepository.save(restaurant));
    }

    public RestaurantResponse save(RestaurantSaveRequest request){
        userClient.findById(request.getUserID());
        Restaurant restaurant = restaurantMapper.toRestaurant(request);
        restaurant.setStatus(RestaurantStatus.PENDING);

        return restaurantMapper.toRestaurantResponse
                (restaurantRepository.save(restaurant));
    }

    public RestaurantResponse update(String restaurantID,RestaurantUpdateRequest request){
        log.info(request.getStatus().getName());
        userClient.findById(request.getUserID());

        if(!restaurantRepository.existsById(restaurantID))
            throw new AppException(ErrorCode.RESTAURANT_NO_EXISTS);

        Restaurant restaurant = restaurantMapper.toRestaurant(request);
        restaurant.setRestaurantID(restaurantID);

        return restaurantMapper.toRestaurantResponse
                (restaurantRepository.save(restaurant));
    }

    public Boolean deleteById(String restaurantID){
        if(!restaurantRepository.existsById(restaurantID))
            throw new AppException(ErrorCode.RESTAURANT_NO_EXISTS);
        try{
            restaurantRepository.deleteById(restaurantID);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Xóa thất bại");
        }
    }

    ItemService itemService;
    ItemMapper itemMapper;

    public ItemRestaurantResponse addItem(String restaurantID, ItemEditRequest request){
        Restaurant restaurant = restaurantRepository.findById(restaurantID)
                .orElseThrow(()->new AppException(ErrorCode.RESTAURANT_NO_EXISTS));
        Long itemID = 1 + restaurant.getMenu().values().stream()
                .map(ItemEntity::getItemID)
                .max(Long::compareTo)
                .orElse(0L);

        ItemRestaurantResponse itemDTO = itemMapper.toItemRestaurantResponse(request);
        itemDTO.setItemID(itemID);

        try{
            restaurant.getMenu().putIfAbsent(itemID,itemDTO);
            restaurantRepository.save(restaurant);
            return itemDTO;
        } catch (Exception e) {
            throw new RuntimeException("Lưu item không thành công");
        }
    }

    public ItemRestaurantResponse editItem(String restaurantID,Long itemID, ItemEditRequest request){
        Restaurant restaurant = restaurantRepository.findById(restaurantID)
                .orElseThrow(()->new AppException(ErrorCode.RESTAURANT_NO_EXISTS));

        itemService.findItem(restaurantID,itemID);

        ItemRestaurantResponse itemDTO = itemMapper.toItemRestaurantResponse(request);
        itemDTO.setItemID(itemID);

        try{
            restaurant.getMenu().put(itemID,itemDTO);
            restaurantRepository.save(restaurant);
            return itemDTO;
        } catch (Exception e) {
            throw new RuntimeException("Cập nhật item không thành công");
        }
    }

    public Boolean deleteItem(String restaurantID,Long itemID){
        Restaurant restaurant = restaurantRepository.findById(restaurantID)
                .orElseThrow(()->new AppException(ErrorCode.RESTAURANT_NO_EXISTS));

        ItemEntity itemDTO = restaurant.getMenu().remove(itemID);

        if(itemID==null)
            return false;

        try{
            restaurantRepository.save(restaurant);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Xóa item không thành công");
        }
    }

    public Boolean deleteMenu(String restaurantID){
        Restaurant restaurant = restaurantRepository.findById(restaurantID)
                .orElseThrow(()->new AppException(ErrorCode.RESTAURANT_NO_EXISTS));

        restaurant.setMenu(Collections.emptyMap());
        if(!restaurant.getMenu().values().isEmpty())
            return false;

        try{
            restaurantRepository.save(restaurant);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Xóa item không thành công");
        }
    }






}
