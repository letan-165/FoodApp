package com.app.AccountService.Service;

import com.app.AccountService.DTO.Request.Client.SendEmailRequest;
import com.app.AccountService.DTO.Request.Client.Sender;
import com.app.AccountService.DTO.Request.OTP.OTPRequest;
import com.app.AccountService.Exception.AppException;
import com.app.AccountService.Exception.ErrorCode;
import com.app.AccountService.Repository.HttpClient.NotificationClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class OTPService {
    NotificationClient notificationClient;
    RedisTemplate<String, Object> redisTemplate;

    @NonFinal
    @Value("${app.time.otp}")
    int timeOtp;

    public int createOTP(String email){
        int otp = ThreadLocalRandom.current().nextInt(100000, 1000000);
        redisTemplate.opsForValue().set(email,otp,timeOtp, TimeUnit.MINUTES);

        notificationClient.sendEmail(SendEmailRequest.builder()
                        .to(Sender.builder()
                                .email(email)
                                .build())
                        .subject("Food App gửi mã OTP")
                        .content("Mã OTP <" + otp +
                                "> của bạn có hiệu lực trong vòng 5phút kể từ khi gửi," +
                                " vui lòng xác nhận nhanh chóng để xác thực và thay đổi thông tin tài khoản một các trọn vẹn.")
                .build());

        return otp;
    }

    public boolean verifyOTP(String gmail, int otp){
        Object getOtp = redisTemplate.opsForValue().get(gmail);
        if(getOtp == null || otp!= (int) getOtp)
            throw new AppException(ErrorCode.OTP_NO_VERIFY);

        redisTemplate.opsForValue().getAndDelete(gmail);
        return true;
    }

}
