package com.app.AccountService.Service;

import com.app.AccountService.DTO.Request.LoginRequest;
import com.app.AccountService.DTO.Request.TokenRequest;
import com.app.AccountService.Entity.Token;
import com.app.AccountService.Entity.User;
import com.app.AccountService.Exception.AppException;
import com.app.AccountService.Exception.ErrorCode;
import com.app.AccountService.Repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthService {
    UserRepository userRepository;
    LoginService loginService;
    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${key.value}")
    String KEY;

    public String login(LoginRequest request) {
        User user = userRepository.findByName(request.getUsername())
                .orElseThrow(()->new AppException(ErrorCode.USER_NO_EXISTS));

        if (!passwordEncoder.matches(request.getPassword(),user.getPassword()))
            throw new AppException(ErrorCode.PASSWORD_UN_VALID);

        return generaToken(user);
    }

    public Boolean instropect(TokenRequest request) throws JOSEException, ParseException {
        if(request.getToken().isEmpty())
            throw new AppException(ErrorCode.TOKEN_LOGOUT);

        JWSVerifier jwsVerifier = new MACVerifier(KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(request.getToken());
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean verified = signedJWT.verify(jwsVerifier);

        if (loginService.getToken(signedJWT.getJWTClaimsSet().getSubject()) == null)
            throw new AppException(ErrorCode.TOKEN_LOGOUT);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("Token expiry time: {}", dateFormat.format(expiryTime));
        return verified && expiryTime.after(new Date());
    }


    public boolean deleteToken(String token){
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            return loginService.deleteToken(jwt.getJWTClaimsSet().getSubject());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String findUserID(String token){
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            throw new AppException(ErrorCode.AUTHENTICATION);
        }

    }


    public String generaToken(User user){
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .jwtID(UUID.randomUUID().toString())
                .issuer("LeTan")
                .subject(user.getUserID())
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.MINUTES).toEpochMilli()))
                .claim("scope",buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        JWSObject jwsObject = new JWSObject(jwsHeader,payload);


        //Lưu login
        loginService.saveToken(user.getUserID(), Token.builder()
                        .tokenID(jwtClaimsSet.getJWTID())
                        .expiryTime(jwtClaimsSet.getExpirationTime().toInstant())
                .build());
        try {
            jwsObject.sign(new MACSigner(KEY.getBytes()));
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
        return jwsObject.serialize();
    }

    String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_"+role.getName());
                if(!CollectionUtils.isEmpty(role.getPermissions())){
                    role.getPermissions().forEach((permission)->stringJoiner.add(permission.getName()));
                }
            });
        }
        log.info(user.getRoles().toString());
        return stringJoiner.toString();
    }

}
