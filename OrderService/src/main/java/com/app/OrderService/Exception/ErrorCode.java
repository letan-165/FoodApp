package com.app.OrderService.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    AUTHENTICATION(1010,"Token not authentication ", HttpStatus.UNAUTHORIZED),
    AUTHORIZED(1009,"You don't have permission", HttpStatus.FORBIDDEN),
    ITEM_NO_EXISTS(1008,"Item not exists", HttpStatus.BAD_REQUEST),
    ITEM_EXISTS(1007,"Item existed", HttpStatus.BAD_REQUEST),
    PAYMENT_NO_EXISTS(1006,"Payment not exists", HttpStatus.BAD_REQUEST),
    PAYMENT_EXISTS(1005,"Payment existed", HttpStatus.BAD_REQUEST),
    RESTAURANT_NO_EXISTS(1004,"Restaurant not exists", HttpStatus.BAD_REQUEST),
    RESTAURANT_EXISTS(1003,"Restaurant existed", HttpStatus.BAD_REQUEST),
    ORDER_NO_EXISTS(1002,"Order not exists", HttpStatus.BAD_REQUEST),
    ORDER_EXISTS(1001,"Order existed", HttpStatus.BAD_REQUEST),
    OTHER_ERROL(9999,"Other errol", HttpStatus.INTERNAL_SERVER_ERROR),;

    int code;
    String message;
    HttpStatus  httpStatus;
}
