package com.app.AccountService.Service;

import com.app.AccountService.Entity.Logout;
import com.app.AccountService.Exception.AppException;
import com.app.AccountService.Exception.ErrorCode;
import com.app.AccountService.Repository.LogoutRepository;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class LogoutService {



    


}
