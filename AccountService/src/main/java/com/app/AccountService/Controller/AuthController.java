package com.app.AccountService.Controller;

import com.app.AccountService.DTO.ApiResponse;
import com.app.AccountService.DTO.Request.OTP.EmailRequest;
import com.app.AccountService.DTO.Request.LoginRequest;
import com.app.AccountService.DTO.Request.OTP.OTPRequest;
import com.app.AccountService.DTO.Request.TokenRequest;
import com.app.AccountService.Service.AuthService;
import com.app.AccountService.Service.OTPService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthController {
     AuthService authService;

     @PostMapping("/login")
     public ApiResponse<String> login(@RequestBody LoginRequest request){
        return ApiResponse.<String>builder()
                .result(authService.login(request))
                .build();
     }

     @PostMapping("/instropect")
     public ApiResponse<Boolean> instropect(@RequestBody TokenRequest request) throws ParseException, JOSEException {
        return ApiResponse.<Boolean>builder()
                .result(authService.instropect(request))
                .build();
     }

     @PostMapping("/logout")
     public ApiResponse<Boolean> logout(@RequestBody TokenRequest request) {
        return ApiResponse.<Boolean>builder()
                .result(authService.deleteToken(request.getToken()))
                .build();
     }

     @PostMapping("/find")
     public ApiResponse<String> findUserID(@RequestBody TokenRequest request) {
        return ApiResponse.<String>builder()
                .result(authService.findUserID(request.getToken()))
                .build();
     }

    OTPService otpService;
    @GetMapping("/otp")
    public ApiResponse<Integer> createOTP(@RequestBody EmailRequest request) {
        return ApiResponse.<Integer>builder()
                .result(otpService.createOTP(request.getEmail()))
                .build();
    }

    @PostMapping("/otp")
    public ApiResponse<Boolean> verifyOTP(@RequestBody OTPRequest request) {
        return ApiResponse.<Boolean>builder()
                .result(otpService.verifyOTP(request.getEmail(), request.getOtp()))
                .build();
    }



}
