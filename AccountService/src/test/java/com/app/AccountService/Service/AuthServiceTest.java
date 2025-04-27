package com.app.AccountService.Service;

import com.app.AccountService.DTO.Request.LoginRequest;
import com.app.AccountService.DTO.Request.TokenRequest;
import com.app.AccountService.DTOMock.EntityMock;
import com.app.AccountService.DTOMock.RequestMock;
import com.app.AccountService.Entity.User;
import com.app.AccountService.Exception.AppException;
import com.app.AccountService.Exception.ErrorCode;
import com.app.AccountService.Repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class AuthServiceTest {
    @Spy
    @InjectMocks
    AuthService authService;

    @Mock UserRepository userRepository;
    @Mock TokenService tokenService;
    @Mock PasswordEncoder passwordEncoder;

    @Mock SignedJWT signedJWT;
    @Mock JWTClaimsSet jwtClaimsSet;

    User user;
    LoginRequest loginRequest;

    @BeforeEach
    void initData() throws ParseException {
        user = EntityMock.userMock();
        loginRequest = RequestMock.loginMock();
        ReflectionTestUtils.setField(authService, "KEY", "MOCKMOCKMOCKMOCKMOCKMOCKMOCKMOCKMOCKMOCKMOCKMOCKMOCK");

        when(signedJWT.getJWTClaimsSet()).thenReturn(jwtClaimsSet);
    }


    @Test
    void generaToken_success() throws ParseException {
        String token = authService.generaToken(user);
        String scope = authService.buildScope(user);

        assertThat(token).isNotEmpty();
        assertThat(scope).isEqualTo("ROLE_ADMIN ADD DELETE UPDATE READ ROLE_CUSTOMER READ");

        verify(tokenService).saveToken(eq(user.getUserID()),any());

        var jwtClaimsSet = SignedJWT.parse(token).getJWTClaimsSet();
        assertThat(jwtClaimsSet.getIssuer()).isEqualTo("LeTan");
        assertThat(jwtClaimsSet.getSubject()).isEqualTo(user.getUserID());
        assertThat(jwtClaimsSet.getClaim("scope")).isEqualTo(scope);

        JWSObject jwsObject = JWSObject.parse(token);
        assertThat(jwsObject.getHeader().getAlgorithm()).isEqualTo(JWSAlgorithm.HS256);
        assertThat(jwsObject.getPayload().toJSONObject()).isEqualTo(jwtClaimsSet.toJSONObject());
    }

    @Test
    void login_success(){
        when(userRepository.findByName(eq(loginRequest.getUsername())))
                .thenReturn(Optional.ofNullable(user));
        when(passwordEncoder.matches(eq(loginRequest.getPassword()),eq(user.getPassword())))
                .thenReturn(true);
        when(authService.generaToken(user)).thenReturn("123456");

        var login = authService.login(loginRequest);

        assertThat(login).isEqualTo("123456");
    }

    @Test
    void login_fail_noExitUser(){
        var exception = assertThrows(AppException.class
                ,()->authService.login(loginRequest));

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.USER_NO_EXISTS);
    }

    @Test
    void login_fail_invalidPassword(){
        when(userRepository.findByName(eq(loginRequest.getUsername())))
                .thenReturn(Optional.ofNullable(user));

        when(passwordEncoder.matches(eq(loginRequest.getPassword()),eq(user.getPassword())))
                .thenReturn(false);

        var exception = assertThrows(AppException.class
                ,()->authService.login(loginRequest));

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.PASSWORD_UN_VALID);
    }

    @Test
    void instropect_success() throws ParseException, JOSEException {


        try (MockedStatic<SignedJWT> signedJWTMockedStatic = mockStatic(SignedJWT.class)) {
            signedJWTMockedStatic.when(() -> SignedJWT.parse(anyString()))
                    .thenReturn(signedJWT);

            when(jwtClaimsSet.getExpirationTime()).thenReturn(Date.from(new Date().toInstant().plus(1, ChronoUnit.MINUTES)));
            when(signedJWT.verify(any())).thenReturn(true);
            when(jwtClaimsSet.getSubject()).thenReturn("userID");
            when(tokenService.getToken(anyString())).thenReturn("token");

            var instropect = authService.instropect(TokenRequest.builder().token("token").build());

            verify(jwtClaimsSet).getExpirationTime();
            verify(signedJWT).verify(any());

            assertThat(instropect).isTrue();
        }
    }

    @Test
    void instropect_fail_tokenEmpty() throws ParseException, JOSEException {
        var exception = assertThrows(AppException.class,
                ()-> authService.instropect(TokenRequest.builder().token("").build()));

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.TOKEN_LOGOUT);
    }

    @Test
    void instropect_fail_parseToken() throws ParseException,JOSEException {
        assertThrows(ParseException.class,
                ()-> authService.instropect(TokenRequest.builder().token("token").build()));
    }

    @Test
    void instropect_fail_noGetToken() throws ParseException, JOSEException {
        when(jwtClaimsSet.getSubject()).thenReturn("userID");

        try(MockedStatic<SignedJWT> signedJWTMockedStatic = mockStatic(SignedJWT.class)){
            signedJWTMockedStatic.when(()->SignedJWT.parse(anyString())).thenReturn(signedJWT);

            var exception = assertThrows(AppException.class,
                    ()->authService.instropect(TokenRequest.builder().token("token").build()));

            assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.TOKEN_LOGOUT);
        }
    }

    @Test
    void instropect_fail_noVerify() throws ParseException, JOSEException {
        when(jwtClaimsSet.getSubject()).thenReturn("userID");
        when(tokenService.getToken(any())).thenReturn("token");
        when(jwtClaimsSet.getExpirationTime()).thenReturn(Date.from(Instant.now().plus(1,ChronoUnit.MINUTES)));
        when(signedJWT.verify(any())).thenReturn(false);

        try(MockedStatic<SignedJWT> signedJWTMockedStatic = mockStatic(SignedJWT.class)){
            signedJWTMockedStatic.when(()->SignedJWT.parse(anyString())).thenReturn(signedJWT);

            var check = authService.instropect(TokenRequest.builder().token("token").build());

            assertThat(check).isFalse();
        }
    }

    @Test
    void instropect_fail_expiryTimeInvalid() throws ParseException, JOSEException {
        when(jwtClaimsSet.getSubject()).thenReturn("userID");
        when(tokenService.getToken(any())).thenReturn("token");
        when(jwtClaimsSet.getExpirationTime()).thenReturn(Date.from(Instant.now()));
        when(signedJWT.verify(any())).thenReturn(true);

        try(MockedStatic<SignedJWT> signedJWTMockedStatic = mockStatic(SignedJWT.class)){
            signedJWTMockedStatic.when(()->SignedJWT.parse(anyString())).thenReturn(signedJWT);

            var check = authService.instropect(TokenRequest.builder().token("token").build());

            assertThat(check).isFalse();
        }
    }

}
