package com.app.AccountService.Service;

import com.app.AccountService.Entity.Token;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class LoginService {
    RedisTemplate<String, Object> redisTemplate;

    public void saveToken(String userID, Token token) {
        //Xóa nếu .đã lưu token
        redisTemplate.opsForValue().getAndDelete(userID);
        Duration duration = Duration.between(Instant.now(), token.getExpiryTime());
        redisTemplate.opsForValue().set(userID
                , token.getTokenID()
                ,duration);
    }

    public String getToken(String userID) {
        return (String) redisTemplate.opsForValue().get(userID);
    }

    public boolean deleteToken(String userID) {
        String tokenID = (String) redisTemplate.opsForValue().getAndDelete(userID);
        return tokenID!=null;
    }

}
