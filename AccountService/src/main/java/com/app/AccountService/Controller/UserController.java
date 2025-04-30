package com.app.AccountService.Controller;

import com.app.AccountService.DTO.ApiResponse;
import com.app.AccountService.DTO.Request.UserRequest;
import com.app.AccountService.DTO.Response.UserResponse;
import com.app.AccountService.Entity.User;
import com.app.AccountService.Service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserController {
    UserService userService;


    @GetMapping
    ApiResponse<List<UserResponse>>findAll(){
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.findAll())
                .build();
    }

    @PostMapping
    ApiResponse<UserResponse>save(@RequestBody @Valid UserRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.save(request))
                .build();
    }

    @GetMapping("/id={userID}")
    ApiResponse<UserResponse>findById(@PathVariable String userID){
        return ApiResponse.<UserResponse>builder()
                .result(userService.findById(userID))
                .build();
    }

    @GetMapping("/name={name}")
    ApiResponse<UserResponse>findByName(@PathVariable String name){
        return ApiResponse.<UserResponse>builder()
                .result(userService.findByName(name))
                .build();
    }

    @PutMapping("/{userID}")
    ApiResponse<UserResponse>update( @PathVariable String userID,@RequestBody @Valid UserRequest request){
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
