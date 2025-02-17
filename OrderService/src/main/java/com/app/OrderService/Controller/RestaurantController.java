package com.app.OrderService.Controller;

import com.app.OrderService.DTO.ApiResponse;
import com.app.OrderService.DTO.Request.RestaurantRequest;
import com.app.OrderService.DTO.Response.RestaurantResponse;
import com.app.OrderService.Entity.Restaurant;
import com.app.OrderService.Service.RestaurantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class RestaurantController {
    RestaurantService restaurantService;

    @GetMapping
    ApiResponse<List<Restaurant>>findAll(){
        return ApiResponse.<List<Restaurant>>builder()
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


}
