package com.app.ChatService.Config;

import com.app.ChatService.Config.WebSocketInterceptor;
import com.app.ChatService.WebSocket.ChatBotHandler;
import com.app.ChatService.WebSocket.ChatHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class WebSocketConfig implements WebSocketConfigurer {
    ChatHandler chatHandler;
    ChatBotHandler chatBotHandler;
    WebSocketInterceptor webSocketInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler, "/chat")
                .addInterceptors(webSocketInterceptor)
                .setAllowedOrigins("*");

        registry.addHandler(chatBotHandler, "/chatbot")
                .addInterceptors(webSocketInterceptor)
                .setAllowedOrigins("*");

    }


}
