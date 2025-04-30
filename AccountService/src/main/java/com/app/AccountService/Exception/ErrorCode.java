package com.app.AccountService.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ERROR_VALIDATION(1014,"Error other invalidation", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1013,"Password invalid", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1012,"Username invalid", HttpStatus.BAD_REQUEST),
    GMAIL_INVALID(1011,"Gmail invalid", HttpStatus.BAD_REQUEST),

    OTP_NO_VERIFY(1010,"OTP no verify", HttpStatus.BAD_REQUEST),
    TOKEN_LOGOUT(1009,"Token had logout", HttpStatus.BAD_REQUEST),
    USER_NAME_EXISTS(1008,"User name existed", HttpStatus.BAD_REQUEST),
    AUTHENTICATION(1007,"Token not authentication ", HttpStatus.UNAUTHORIZED),
    AUTHORIZED(1006,"You don't have permission", HttpStatus.FORBIDDEN),
    PASSWORD_UN_VALID(1005,"Password don't valid", HttpStatus.BAD_REQUEST),
    ROLE_EXISTS(1004,"Role existed", HttpStatus.BAD_REQUEST),
    ROLE_NO_EXISTS(1003,"Role not exist", HttpStatus.BAD_REQUEST),
    USER_EXISTS(1002,"User existed", HttpStatus.BAD_REQUEST),
    USER_NO_EXISTS(1001,"User not exists", HttpStatus.BAD_REQUEST),
    OTHER_ERROL(9999,"Other errol", HttpStatus.INTERNAL_SERVER_ERROR);

    int code;
    String message;
    HttpStatusCode httpStatus;
}
