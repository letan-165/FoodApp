package com.app.AccountService.Controller;

import com.app.AccountService.DTO.ApiResponse;
import com.app.AccountService.DTO.Request.LoginRequest;
import com.app.AccountService.DTO.Request.TokenRequest;
import com.app.AccountService.Service.AuthService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
