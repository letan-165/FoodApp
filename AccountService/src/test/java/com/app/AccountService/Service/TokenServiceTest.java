package com.app.AccountService.Service;
import com.app.AccountService.Entity.Token;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class TokenServiceTest {
    @InjectMocks
    TokenService tokenService;

    @Mock
    RedisTemplate<String, Object> redisTemplate;

    @Mock
    ValueOperations<String,Object> valueOperations;

    String userID;
    String tokenID;

    @BeforeEach
    void initData(){
        userID = "userID";
        tokenID = "tokenID";
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void testSaveToken() {
        tokenService.saveToken(userID, Token.builder()
                        .tokenID(tokenID)
                        .expiryTime(Instant.now().plus(1,ChronoUnit.MINUTES))
                .build());

        verify(valueOperations).getAndDelete(eq(userID));
        verify(valueOperations).set(eq(userID), eq(tokenID), any(Duration.class));
    }

    @Test
    void testGetToken() {
        when(valueOperations.get(userID)).thenReturn(tokenID);
        String result  = tokenService.getToken(userID);

        verify(valueOperations).get(eq(userID));
        assertThat(tokenID).isEqualTo(result );
    }

    @Test
    void testDeleteToken() {
        when(valueOperations.getAndDelete(userID)).thenReturn(tokenID);

        boolean check = tokenService.deleteToken(userID);

        verify(valueOperations).getAndDelete(eq(userID));
        assertThat(check).isTrue();
    }

    @Test
    void testDeleteToken_notExist() {
        when(valueOperations.getAndDelete(userID)).thenReturn(null);

        boolean check = tokenService.deleteToken(userID);

        verify(valueOperations).getAndDelete(eq(userID));
        assertThat(check).isFalse();
    }
}
