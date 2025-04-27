package com.app.AccountService.DTOMock;

import com.app.AccountService.DTO.Request.LoginRequest;
import com.app.AccountService.DTO.Request.TokenRequest;
import com.app.AccountService.DTO.Request.UserRequest;

public class RequestMock {
    public static UserRequest userMock(){
        return UserRequest.builder()
                .name("name")
                .password("password")
                .phone("phone")
                .gmail("gmail")
                .otp(123456)
                .build();
    }

    public static LoginRequest loginMock() {
        return LoginRequest.builder()
                .username("username")
                .password("password")
                .build();
    }
}
