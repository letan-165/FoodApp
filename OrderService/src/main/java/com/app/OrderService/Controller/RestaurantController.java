package com.app.OrderService.Controller;

import com.app.OrderService.DTO.ApiResponse;
import com.app.OrderService.DTO.Request.RestaurantRequest;
import com.app.OrderService.DTO.Response.RestaurantResponse;
import com.app.OrderService.Entity.Restaurant;
import com.app.OrderService.Model.ItemModel;
import com.app.OrderService.Service.RestaurantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class RestaurantController {
    RestaurantService restaurantService;

    @GetMapping
    ApiResponse<List<RestaurantResponse>>findAll(){
        return ApiResponse.<List<RestaurantResponse>>builder()
                .result(restaurantService.findAll())
                .build();
    }

    @PostMapping
    ApiResponse<RestaurantResponse>save(@RequestBody RestaurantRequest request){
        return ApiResponse.<RestaurantResponse>builder()
                .result(restaurantService.save(request))
                .build();
    }

    @PostMapping("/{restaurantID}")
    ApiResponse<RestaurantResponse>findById(@PathVariable String restaurantID){
        return ApiResponse.<RestaurantResponse>builder()
                .result(restaurantService.findById(restaurantID))
                .build();
    }
    @PutMapping("/{restaurantID}")
    ApiResponse<RestaurantResponse>update( @PathVariable String restaurantID,@RequestBody RestaurantRequest request){
        return ApiResponse.<RestaurantResponse>builder()
                .result(restaurantService.update(restaurantID,request))
                .build();
    }

    @DeleteMapping("/{restaurantID}")
    ApiResponse<Boolean>delete(@PathVariable String restaurantID){
        return ApiResponse.<Boolean>builder()
                .result(restaurantService.delete(restaurantID))
                .build();
    }

    @PutMapping("/id={restaurantID}/item/add")
    ApiResponse<ItemModel>addItem(@PathVariable String restaurantID, @RequestBody ItemModel request){
        return ApiResponse.<ItemModel>builder()
                .result(restaurantService.addItem(restaurantID,request))
                .build();
    }

    @PutMapping("/id={restaurantID}/item={itemID}/edit")
    ApiResponse<ItemModel>editItem(@PathVariable String restaurantID,@PathVariable Long itemID,@RequestBody ItemModel request){
        return ApiResponse.<ItemModel>builder()
                .result(restaurantService.editItem(restaurantID,itemID,request))
                .build();
    }

    @PutMapping("/id={restaurantID}/item={itemID}/delete")
    ApiResponse<Boolean>deleteItem(@PathVariable String restaurantID,@PathVariable Long itemID){
        return ApiResponse.<Boolean>builder()
                .result(restaurantService.deleteItem(restaurantID,itemID))
                .build();
    }

    @PutMapping("/id={restaurantID}/menu/delete")
    ApiResponse<Boolean>deleteMenu(@PathVariable String restaurantID){
        return ApiResponse.<Boolean>builder()
                .result(restaurantService.deleteMenu(restaurantID))
                .build();
    }




}
