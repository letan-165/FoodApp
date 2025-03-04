package com.app.AccountService.Controller;

import com.app.AccountService.DTO.ApiResponse;
import com.app.AccountService.DTO.Request.UserRequest;
import com.app.AccountService.DTO.Response.UserResponse;
import com.app.AccountService.Entity.User;
import com.app.AccountService.Service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserController {
    UserService userService;


    @GetMapping
    ApiResponse<List<User>>findAll(){
        return ApiResponse.<List<User>>builder()
                .result(userService.findAll())
                .build();
    }

    @PostMapping
    ApiResponse<UserResponse>save(@RequestBody UserRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.save(request))
                .build();
    }

    @PostMapping("/id={userID}")
    ApiResponse<UserResponse>findById(@PathVariable String userID){
        return ApiResponse.<UserResponse>builder()
                .result(userService.findById(userID))
                .build();
    }

    @PostMapping("/name={userID}")
    ApiResponse<UserResponse>findByName(@PathVariable String userID){
        return ApiResponse.<UserResponse>builder()
                .result(userService.findByName(userID))
                .build();
    }

    @PutMapping("/{userID}")
    ApiResponse<UserResponse>update( @PathVariable String userID,@RequestBody UserRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.update(userID,request))
                .build();
    }

    @DeleteMapping("/{userID}")
    ApiResponse<Boolean>delete(@PathVariable String userID){
        return ApiResponse.<Boolean>builder()
                .result(userService.delete(userID))
                .build();
    }


}
