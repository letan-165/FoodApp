package com.app.NotificationService.Service;

import com.app.NotificationService.DTO.BaseDTO.Sender;
import com.app.NotificationService.DTO.Request.Email.EmailFullRequest;
import com.app.NotificationService.DTO.Request.Email.SendEmailRequest;
import com.app.NotificationService.DTO.Response.Client.UserResponse;
import com.app.NotificationService.DTO.Response.Email.EmailResponse;
import com.app.NotificationService.Mapper.EmailMapper;
import com.app.NotificationService.Repository.HttpClient.EmailClient;
import com.app.NotificationService.Repository.HttpClient.UserClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    EmailClient emailClient;
    UserClient userClient;

    @NonFinal
    @Value("${app.client.contact.email}")
    String emailContact;

    @NonFinal
    @Value("${app.client.contact.name}")
    String nameEmail;

    @NonFinal
    @Value("${key.email}")
    String keyEmail;





    public EmailResponse sendEmail(SendEmailRequest request){
        UserResponse user = userClient.findById(request.getToUserID()).getResult();

        EmailFullRequest emailFullRequest = EmailFullRequest.builder()
                .sender(Sender.builder()
                        .email(emailContact)
                        .name(nameEmail)
                        .build())
                .to(new ArrayList<>(List.of(Sender.builder()
                                .name(user.getName())
                                .email(user.getGmail())
                        .build())))
                .htmlContent("<h2>Chào bạn,</h2></br><p>"+ request.getContent() +"</p>")
                .subject(request.getSubject())
                .build();

        return emailClient.sendEmail(keyEmail,emailFullRequest);
    }

}
