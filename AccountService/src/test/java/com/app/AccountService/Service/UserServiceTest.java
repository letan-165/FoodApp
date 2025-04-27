package com.app.AccountService.Service;

import com.app.AccountService.DTO.ApiResponse;
import com.app.AccountService.DTO.Request.UserRequest;
import com.app.AccountService.DTO.Response.UserResponse;
import com.app.AccountService.DTOMock.EntityMock;
import com.app.AccountService.DTOMock.RequestMock;
import com.app.AccountService.DTOMock.ResponseMock;
import com.app.AccountService.Entity.User;
import com.app.AccountService.Enum.RoleEnum;
import com.app.AccountService.Exception.AppException;
import com.app.AccountService.Exception.ErrorCode;
import com.app.AccountService.Mapper.UserMapper;
import com.app.AccountService.Repository.HttpClient.CartClient;
import com.app.AccountService.Repository.RoleRepository;
import com.app.AccountService.Repository.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceTest {
    @InjectMocks
    UserService userService;

    @Mock UserRepository userRepository;
    @Mock RoleRepository roleRepository;
    @Mock UserMapper userMapper;
    @Mock PasswordEncoder passwordEncoder;
    @Mock CartClient cartClient;
    @Mock OTPService otpService;

    User user;
    UserRequest userRequest;
    UserResponse userResponse;
    String userID;

    @BeforeEach
    void initData(){
        userID = "userID";
        userRequest = RequestMock.userMock();
        userResponse = ResponseMock.userMock();
        user = EntityMock.userMock();
    }

    @Test
    void findAll_success(){
        userService.findAll();
        verify(userRepository).findAll();
    }

    @Test
    void save_success(){
        when(userRepository.existsByName(eq(userRequest.getName()))).thenReturn(false);
        when(passwordEncoder.encode(eq(userRequest.getPassword()))).thenReturn("enPass");
        when(userMapper.toUserResponse(
                        userRepository.save(userMapper.toUser(any(UserRequest.class))))).thenReturn(userResponse);
        when(cartClient.save(eq(userResponse.getUserID()))).thenReturn(new ApiResponse<>());


        var response = userService.save(userRequest);
        verify(otpService).verifyOTP(eq(userRequest.getGmail()),eq(userRequest.getOtp()));
        assertThat(response).isEqualTo(userResponse);
    }

    @Test
    void save_fail_userNameExists(){
        when(userRepository.existsByName(eq(userRequest.getName()))).thenReturn(true);

        var exception = assertThrows(AppException.class,
                ()-> userService.save(userRequest));

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.USER_EXISTS);
    }
    @Test
    void save_fail_cartClientFail(){
        when(userRepository.existsByName(eq(userRequest.getName()))).thenReturn(false);

        var exception = assertThrows(RuntimeException.class,
                ()-> userService.save(userRequest));

        assertThat(exception.getMessage()).isEqualTo("False create cart");

    }


    @Test
    void update_success(){
        when(userRepository.findById(userID)).thenReturn(Optional.ofNullable(user));
        when(userRepository.findByName(eq(userRequest.getName()))).thenReturn(Optional.ofNullable(user));
        when(userMapper.toUser(any())).thenReturn(user);
        when(userMapper.toUserResponse(
                userRepository.save(any()))).thenReturn(userResponse);

        var response = userService.update(userID,userRequest);

        verify(otpService).verifyOTP(eq(userRequest.getGmail()),eq(userRequest.getOtp()));
        assertThat(response).isEqualTo(userResponse);
    }

    @Test
    void update_fail_notFindByID(){
        when(userRepository.findById(userID)).thenReturn(Optional.empty());
        var exception = assertThrows(AppException.class,
                () -> userService.update(userID,userRequest));

        assertThat(ErrorCode.USER_NO_EXISTS).isEqualTo(exception.getErrorCode());
    }

    @Test
    void update_fail_notFindByName(){
        when(userRepository.findById(userID)).thenReturn(Optional.ofNullable(user));
        User user2 = user.toBuilder()
                .userID("userID2")
                .name("name2")
                .build();
        when(userRepository.findByName(userRequest.getName())).thenReturn(Optional.ofNullable(user2));

        var exception = assertThrows(AppException.class,
                () -> userService.update(userID,userRequest));

        assertThat(ErrorCode.USER_NAME_EXISTS).isEqualTo(exception.getErrorCode());
    }




}
