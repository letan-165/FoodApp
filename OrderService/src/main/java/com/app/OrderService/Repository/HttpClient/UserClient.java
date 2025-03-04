package com.app.OrderService.Repository.HttpClient;

import com.app.OrderService.DTO.ApiResponse;
import com.app.OrderService.DTO.Response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user",url = "${app.service.account}")
public interface UserClient {
    @PostMapping(value = "/user/id={userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserResponse> findById (@PathVariable String userID);

    @PostMapping(value = "/user/name={name}", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserResponse> findByName (@PathVariable String name);
}
