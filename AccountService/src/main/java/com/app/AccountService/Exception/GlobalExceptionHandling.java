package com.app.AccountService.Exception;

import com.app.AccountService.DTO.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandling {

    ResponseEntity<ApiResponse> toResponseEntity(ErrorCode errorCode){
        return ResponseEntity.status(errorCode.httpStatus)
                .body(ApiResponse.builder()
                        .code(errorCode.code)
                        .message(errorCode.message)
                        .build());
    }
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingException(){
        return toResponseEntity(ErrorCode.OTHER_ERROL);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException e){
        return toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(value = AuthorizationDeniedException.class)
    ResponseEntity<ApiResponse> handlingDeniedException(){
        return toResponseEntity(ErrorCode.AUTHORIZED);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception){
        String message =  exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode;
        try {
            errorCode = ErrorCode.valueOf(message);
        } catch (IllegalArgumentException e){
            errorCode = ErrorCode.ERROR_VALIDATION;
        }
        return toResponseEntity(errorCode);
    }
}
