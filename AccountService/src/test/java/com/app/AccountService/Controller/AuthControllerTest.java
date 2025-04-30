package com.app.AccountService.Controller;

import com.app.AccountService.DTO.Request.LoginRequest;
import com.app.AccountService.DTO.Request.OTP.EmailRequest;
import com.app.AccountService.DTO.Request.OTP.OTPRequest;
import com.app.AccountService.DTO.Request.TokenRequest;
import com.app.AccountService.DTOMock.RequestMock;
import com.app.AccountService.Service.AuthService;
import com.app.AccountService.Service.OTPService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    AuthService authService;

    @MockitoBean
    OTPService otpService;

    LoginRequest loginRequest;
    TokenRequest tokenRequest;
    EmailRequest emailRequest;
    OTPRequest otpRequest;
    String token;

    @BeforeEach
    void initData(){
        loginRequest = RequestMock.loginMock();
        tokenRequest = RequestMock.tokenRequest();
        emailRequest = RequestMock.emailRequest();
        otpRequest = RequestMock.otpRequest();
        token =  tokenRequest.getToken();
    }

    <T> void mockMvcSetting(String endpoint, String content, T result) throws Exception {
        mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value(1000))
                .andExpect(jsonPath("result").value(result));
    }

    @Test
    void login_success() throws Exception{
        when(authService.login(loginRequest)).thenReturn(token);
        String content = objectMapper.writeValueAsString(loginRequest);

        mockMvcSetting("/auth/login", content,token);
    }

    @Test
    void instropect_success() throws Exception{
        when(authService.instropect(tokenRequest)).thenReturn(true);
        String content = objectMapper.writeValueAsString(tokenRequest);
        mockMvcSetting("/auth/instropect", content,true);
    }

    @Test
    void logout_success() throws Exception{
        when(authService.deleteToken(token)).thenReturn(true);
        String content = objectMapper.writeValueAsString(tokenRequest);
        mockMvcSetting("/auth/logout", content,true);
    }

    @Test
    void findUserID_success() throws Exception{
        when(authService.findUserID(token)).thenReturn("userID");
        String content = objectMapper.writeValueAsString(tokenRequest);
        mockMvcSetting("/auth/find", content,"userID");
    }

    @Test
    void createOTP_success() throws Exception{
        when(otpService.createOTP(emailRequest.getEmail())).thenReturn(123456);
        String content = objectMapper.writeValueAsString(emailRequest);

        mockMvc.perform(get("/auth/otp")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value(1000))
                .andExpect(jsonPath("result").value(123456));
    }

    @Test
    void verifyOTP_success() throws Exception{
        when(otpService.verifyOTP(otpRequest.getEmail(),otpRequest.getOtp())).thenReturn(true);
        String content = objectMapper.writeValueAsString(otpRequest);
        mockMvcSetting("/auth/otp", content,true);
    }
}
