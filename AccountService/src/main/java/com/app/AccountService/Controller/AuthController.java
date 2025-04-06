package com.app.AccountService.Controller;

import com.app.AccountService.DTO.ApiResponse;
import com.app.AccountService.DTO.Request.LoginRequest;
import com.app.AccountService.DTO.Request.TokenRequest;
import com.app.AccountService.Service.AuthService;
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

//     @GetMapping("/logout")
//     public ApiResponse<List<Logout>> findAll() {
//        return ApiResponse.<List<Logout>>builder()
//                .result(authService.findAll())
//                .build();
//     }
//
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




}
