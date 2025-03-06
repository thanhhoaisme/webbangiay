package com.example.BE.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode  {
    UNCATEGORIZE_EXCEPTION("Uncategorize exception", 9999, HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY("Invalid message key", 1001,HttpStatus.BAD_REQUEST),
    USER_EXISTED("User already exist", 1002,HttpStatus.BAD_REQUEST),
    USERNAME_INVALID("Username MUST BE AT LEAST {min} CHARACTERS", 1003,HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID("Password MUST BE AT LEAST {min} CHARACTERS", 1004,HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED("User not exist", 1005,HttpStatus.NOT_FOUND),
    UNAUTHENTICATED("Unauthenticated", 1006,HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("You dont have permission", 1007,HttpStatus.FORBIDDEN),
    INVALID_DOB("Your Age MUST BE AT LEAST {min}", 1008,HttpStatus.BAD_REQUEST),
    ;

    private int code;
    private String message;
    private HttpStatusCode statusCode;

    ErrorCode(String message, int code,HttpStatusCode statusCode) {
        this.message = message;
        this.code = code;
        this.statusCode = statusCode;
    }


}
