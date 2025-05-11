package com.app.OrderService.Controller;

import com.app.OrderService.DTO.ApiResponse;
import com.app.OrderService.DTO.Request.Restaurant.ItemEditRequest;
import com.app.OrderService.DTO.Request.Restaurant.RestaurantSaveRequest;
import com.app.OrderService.DTO.Request.Restaurant.RestaurantUpdateRequest;
import com.app.OrderService.DTO.Response.Restaurant.ItemRestaurantResponse;
import com.app.OrderService.DTO.Response.Restaurant.RestaurantResponse;
import com.app.OrderService.Service.RestaurantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RestaurantController {
    RestaurantService restaurantService;

    @GetMapping
    ApiResponse<List<RestaurantResponse>> findAll(){
        return ApiResponse.<List<RestaurantResponse>>builder()
                .message("Lấy danh sách cửa hàng")
                .result(restaurantService.findAll())
                .build();
    }

    @GetMapping("/user/id={userID}")
    ApiResponse<List<RestaurantResponse>> findAllByUserID(@PathVariable String userID){
        return ApiResponse.<List<RestaurantResponse>>builder()
                .message("Lấy danh sách cửa hàng của "+userID)
                .result(restaurantService.findAllByUserID(userID))
                .build();
    }

    @GetMapping("/{restaurantID}")
    ApiResponse<RestaurantResponse> findById(@PathVariable String restaurantID){
        return ApiResponse.<RestaurantResponse>builder()
                .message("Tìm cửa hàng dựa trên ID: "+restaurantID)
                .result(restaurantService.findById(restaurantID))
                .build();
    }

    @PostMapping
    ApiResponse<RestaurantResponse> save(@RequestBody RestaurantSaveRequest request){
        return ApiResponse.<RestaurantResponse>builder()
                .message("Đăng kí nhà hàng ")
                .result(restaurantService.save(request))
                .build();
    }

    @PutMapping("/{restaurantID}")
    ApiResponse<RestaurantResponse> update(@PathVariable String restaurantID,@RequestBody RestaurantUpdateRequest request){
        return ApiResponse.<RestaurantResponse>builder()
                .message("Cập nhật thông tin nhà hàng ")
                .result(restaurantService.update(restaurantID,request))
                .build();
    }

    @DeleteMapping("/{restaurantID}")
    ApiResponse<Boolean> deleteById(@PathVariable String restaurantID){
        return ApiResponse.<Boolean>builder()
                .message("Xóa nhà hàng ")
                .result(restaurantService.deleteById(restaurantID))
                .build();
    }

    @PutMapping("/id={restaurantID}/item/add")
    ApiResponse<ItemRestaurantResponse> addItem(@PathVariable String restaurantID,@RequestBody ItemEditRequest request){
        return ApiResponse.<ItemRestaurantResponse>builder()
                .message("Thêm mới món ăn")
                .result(restaurantService.addItem( restaurantID,request))
                .build();
    }

    @PutMapping("/id={restaurantID}/item/id={itemID}/edit")
    ApiResponse<ItemRestaurantResponse> editItem(@PathVariable String restaurantID,@PathVariable Long itemID,@RequestBody ItemEditRequest request){
        return ApiResponse.<ItemRestaurantResponse>builder()
                .message("Cập nhật món ăn: "+itemID)
                .result(restaurantService.editItem( restaurantID,itemID,request))
                .build();
    }

    @PutMapping("/id={restaurantID}/item/id={itemID}/delete")
    ApiResponse<Boolean> deleteItem(@PathVariable String restaurantID,@PathVariable Long itemID){
        return ApiResponse.<Boolean>builder()
                .message("Xóa món ăn: "+itemID)
                .result(restaurantService.deleteItem( restaurantID,itemID))
                .build();
    }

    @PutMapping("/id={restaurantID}/menu/delete")
    ApiResponse<Boolean> deleteMenu(@PathVariable String restaurantID){
        return ApiResponse.<Boolean>builder()
                .message("Xóa menu: "+restaurantID)
                .result(restaurantService.deleteMenu( restaurantID))
                .build();
    }

}
