package com.scheduleapp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "Schedule not found"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "Invalid Password");

    private final HttpStatus httpStatus;
    private final String message;
}
