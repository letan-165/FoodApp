package com.app.OtherService.Repository.HttpClient;

import com.app.OtherService.DTO.ApiResponse;
import com.app.OtherService.DTO.Response.Client.UserResponse;
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
