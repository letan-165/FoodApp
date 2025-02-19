package com.app.AccountService.Service;

import com.app.AccountService.DTO.Request.LoginRequest;
import com.app.AccountService.DTO.Request.TokenRequest;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
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
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @NonFinal
    @Value("${key.value}")
    String KEY;

    public String login(LoginRequest request) {
        User user = userRepository.findByName(request.getUsername()).orElseThrow(()->new AppException(ErrorCode.USER_NO_EXISTS));
        if (!passwordEncoder.matches(request.getPassword(),user.getPassword())) {
            throw new AppException(ErrorCode.PASSWORD_UN_VALID);
        }
        return generaToken(user);
    }

    public Boolean instropect(TokenRequest request) throws JOSEException, ParseException {
        JWSVerifier jwsVerifier = new MACVerifier(KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(request.getToken());
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean verified = signedJWT.verify(jwsVerifier);

        return verified && expiryTime.after(new Date());
    }

    public String generaToken(User user){
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .jwtID(UUID.randomUUID().toString())
                .issuer("LeTan")
                .subject(user.getUserID())
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope",buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        JWSObject jwsObject = new JWSObject(jwsHeader,payload);

        try {
            jwsObject.sign(new MACSigner(KEY.getBytes()));
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
        return jwsObject.serialize();
    }

    String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        stringJoiner.add("ROLE_"+user.getRole().getName());
        if(!CollectionUtils.isEmpty(user.getRole().getPermissions())){
            user.getRole().getPermissions().forEach((permission)->stringJoiner.add(permission.getName()));
        }
        return stringJoiner.toString();
    }

}
