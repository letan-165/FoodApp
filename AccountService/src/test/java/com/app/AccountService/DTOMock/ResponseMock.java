package com.app.AccountService.DTOMock;

import com.app.AccountService.DTO.Response.UserResponse;

import java.util.List;

public class ResponseMock {
    public static UserResponse userMock(){
        return UserResponse.builder()
                .userID("userID")
                .name("name")
                .phone("phone")
                .gmail("gmail@gmail")
                .roles(List.of("ADMIN","CUSTOMER"))
                .build();
    }
}
