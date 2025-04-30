package com.app.AccountService.DTOMock;

import com.app.AccountService.DTO.Request.LoginRequest;
import com.app.AccountService.DTO.Request.OTP.EmailRequest;
import com.app.AccountService.DTO.Request.OTP.OTPRequest;
import com.app.AccountService.DTO.Request.TokenRequest;
import com.app.AccountService.DTO.Request.UserRequest;

public class RequestMock {
    public static UserRequest userMock(){
        return UserRequest.builder()
                .name("name")
                .password("password")
                .phone("phone")
                .gmail("gmail@gmail")
                .otp(123456)
                .build();
    }

    public static LoginRequest loginMock() {
        return LoginRequest.builder()
                .username("username")
                .password("password")
                .build();
    }

    public static TokenRequest tokenRequest() {
        return TokenRequest.builder()
                .token("token")
                .build();
    }

    public static EmailRequest emailRequest() {
        return EmailRequest.builder()
                .email("tan@email")
                .build();
    }
    public static OTPRequest otpRequest() {
        return OTPRequest.builder()
                .email("tan@email")
                .otp(123456)
                .build();
    }
}
