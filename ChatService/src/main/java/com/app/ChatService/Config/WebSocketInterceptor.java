package com.app.ChatService.Config;

import com.app.ChatService.DTO.Request.Client.TokenRequest;
import com.app.ChatService.Repository.HttpClient.UserClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class WebSocketInterceptor implements HandshakeInterceptor {

    private final UserClient userClient;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        String token = servletRequest.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            return false;
        }

        String jwt = token.replace("Bearer ", "");
        String userID = userClient.getUserIDFromToken(TokenRequest.builder().token(jwt).build()).getResult();

        if (userID == null || userID.isEmpty()) {
            return false;
        }

        attributes.put("userID", userID);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    }
}
