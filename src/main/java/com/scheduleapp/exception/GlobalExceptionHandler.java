package com.scheduleapp.exception;

import com.scheduleapp.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomException(CustomException e) {
        return new ResponseEntity<>(
                new ErrorResponseDto(e.getErrorCode().getHttpStatus(), e.getErrorCode().getMessage()),
                e.getErrorCode().getHttpStatus());
    }
}
