package com.app.OtherService.Controller;

import com.app.OtherService.DTO.ApiResponse;
import com.app.OtherService.DTO.Request.Client.ChatBotRequest;
import com.app.OtherService.DTO.Response.Client.ChatBotResponse;
import com.app.OtherService.Service.ChatBotService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatbot")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class ChatBotController {
    ChatBotService chatBotService;

    @PostMapping
    ApiResponse<ChatBotResponse> sendChatBot(@RequestBody ChatBotRequest request){
        log.info(request.toString());
        return ApiResponse.<ChatBotResponse>builder()
                .message("Letan gửi chatbot")
                .result(chatBotService.sendChatBot(request))
                .build();
    }
}
