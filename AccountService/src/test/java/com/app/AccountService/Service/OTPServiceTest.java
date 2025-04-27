package com.app.AccountService.Service;

import com.app.AccountService.DTO.Request.Client.SendEmailRequest;
import com.app.AccountService.Exception.AppException;
import com.app.AccountService.Repository.HttpClient.NotificationClient;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OTPServiceTest {

    @InjectMocks
    OTPService otpService;
    @Mock
    RedisTemplate<String, Object> redisTemplate;

    @Mock
    ValueOperations<String, Object> valueOperations;

    @Mock
    NotificationClient notificationClient;

    long timeOtp;

    @BeforeEach
    void initData(){
        timeOtp = 1;
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }


    @Test
    void testCreateOTP(){
        String emailTest = "test@email";
        int otp = otpService.createOTP(emailTest);
        assertThat(otp).isGreaterThanOrEqualTo(100000).isLessThanOrEqualTo(999999);
        verify(valueOperations).set(eq(emailTest), eq(otp), eq(timeOtp),any(TimeUnit.class));
        verify(notificationClient).sendEmail(any(SendEmailRequest.class));
    }

    @Test
    void testVerifyOTP_exists(){
        String email = "test@gmail";
        int otp = 123345;
        when(valueOperations.get(email)).thenReturn(otp);
        boolean verifyOTP = otpService.verifyOTP(email,otp);

        verify(valueOperations).get(email);
        verify(valueOperations).getAndDelete(email);
        assertThat(verifyOTP).isTrue();
    }

    @Test
    void testVerifyOTP_noExists(){
        String email = "test@gmail";
        int otp = 123456;
        when(valueOperations.get(email)).thenReturn(123457);

        assertThatThrownBy(()-> otpService.verifyOTP(email,otp)).isInstanceOf(AppException.class);

        verify(valueOperations).get(email);
        verify(valueOperations, never()).getAndDelete(email);

    }


}
