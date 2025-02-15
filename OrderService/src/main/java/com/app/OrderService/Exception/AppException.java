package com.app.OrderService.Exception;

public class AppException extends Exception{
    AppException(ErrorCode e){
        super(e.getMessage());
    }
}
