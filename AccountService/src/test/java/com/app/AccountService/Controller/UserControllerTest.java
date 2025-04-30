package com.app.AccountService.Controller;

import com.app.AccountService.DTO.Request.UserRequest;
import com.app.AccountService.DTO.Response.UserResponse;
import com.app.AccountService.DTOMock.RequestMock;
import com.app.AccountService.DTOMock.ResponseMock;
import com.app.AccountService.Exception.AppException;
import com.app.AccountService.Exception.ErrorCode;
import com.app.AccountService.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    UserService userService;

    UserRequest userRequest;
    UserResponse userResponse;

    String userID;

    @BeforeEach
    void initData(){
        userRequest = RequestMock.userMock();
        userResponse = ResponseMock.userMock();

        userID = userResponse.getUserID();
    }

    @Test
    void findAll_success() throws Exception {
        List<UserResponse> userResponseList = new ArrayList<>(List.of(userResponse,userResponse));

        when(userService.findAll()).thenReturn(userResponseList);

        mockMvc.perform(get("/user")
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value(1000))
                .andExpect(jsonPath("result.length()").value(2));
    }

    @Test
    void save_success() throws Exception {
        var content = objectMapper.writeValueAsString(userRequest);

        when(userService.save(userRequest)).thenReturn(userResponse);

        mockMvc.perform(post("/user")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value(1000))
                .andExpect(jsonPath("result.userID").value(userResponse.getUserID()))
                .andExpect(jsonPath("result.name").value(userResponse.getName()))
                .andExpect(jsonPath("result.phone").value(userResponse.getPhone()))
                .andExpect(jsonPath("result.gmail").value(userResponse.getGmail()))
                .andExpect(jsonPath("result.roles.length()").value(2));

    }

    @Test
    void save_fail_usernameInvalid() throws Exception {
        userRequest.setName("tan");
        ErrorCode errorCode = ErrorCode.USERNAME_INVALID;
        var content = objectMapper.writeValueAsString(userRequest);

        when(userService.save(userRequest)).thenReturn(userResponse);

        mockMvc.perform(post("/user")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(errorCode.getCode()))
                .andExpect(jsonPath("message").value(errorCode.getMessage()));

    }

    @Test
    void save_fail_passwordInvalid() throws Exception {
        userRequest.setPassword("12345678910");
        ErrorCode errorCode = ErrorCode.PASSWORD_INVALID;
        var content = objectMapper.writeValueAsString(userRequest);

        when(userService.save(userRequest)).thenReturn(userResponse);

        mockMvc.perform(post("/user")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(errorCode.getCode()))
                .andExpect(jsonPath("message").value(errorCode.getMessage()));

    }

    @Test
    void save_fail_gmailInvalid() throws Exception {
        userRequest.setGmail("gmail");
        ErrorCode errorCode = ErrorCode.GMAIL_INVALID;
        var content = objectMapper.writeValueAsString(userRequest);

        when(userService.save(userRequest)).thenReturn(userResponse);

        mockMvc.perform(post("/user")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(errorCode.getCode()))
                .andExpect(jsonPath("message").value(errorCode.getMessage()));

    }

    @Test
    void findByUserID_success() throws Exception {
        when(userService.findById(userID)).thenReturn(userResponse);

        mockMvc.perform(get("/user/id={userID}",userID)
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value(1000))
                .andExpect(jsonPath("result.userID").value(userResponse.getUserID()))
                .andExpect(jsonPath("result.name").value(userResponse.getName()))
                .andExpect(jsonPath("result.phone").value(userResponse.getPhone()))
                .andExpect(jsonPath("result.gmail").value(userResponse.getGmail()))
                .andExpect(jsonPath("result.roles.length()").value(2));

    }

    @Test
    void findByUserID_fail_notFindUser() throws Exception {
        ErrorCode errorCode = ErrorCode.USER_NO_EXISTS;

        when(userService.findById(userID)).thenThrow(new AppException(errorCode));

        mockMvc.perform(get("/user/id={userID}",userID)
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(errorCode.getCode()))
                .andExpect(jsonPath("message").value(errorCode.getMessage()));
    }






}
