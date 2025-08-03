package com.scheduleapp.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponseDto {
    private final int code;
    private final String message;

    public ErrorResponseDto(HttpStatus httpStatus, String message) {
        this.code = httpStatus.value();
        this.message = message;
    }
}