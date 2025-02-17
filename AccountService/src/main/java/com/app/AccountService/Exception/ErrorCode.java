package com.app.AccountService.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    OTHER_ERROL(9999,"Other errol", HttpStatus.INTERNAL_SERVER_ERROR),
    ROLE_EXISTS(1004,"Role existed", HttpStatus.BAD_REQUEST),
    ROLE_NO_EXISTS(1003,"Role not exist", HttpStatus.BAD_REQUEST),
    USER_EXISTS(1002,"User existed", HttpStatus.BAD_REQUEST),
    USER_NO_EXISTS(1001,"User not exists", HttpStatus.BAD_REQUEST);

    int code;
    String message;
    HttpStatusCode httpStatus;
}
