package com.scheduleapp.exception;

import com.scheduleapp.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomException(CustomException e) {
        HttpStatus httpStatus = e.getErrorCode().getHttpStatus();
        String message = e.getErrorCode().getMessage();

        return new ResponseEntity<>(new ErrorResponseDto(httpStatus, message), httpStatus);
    }
}
