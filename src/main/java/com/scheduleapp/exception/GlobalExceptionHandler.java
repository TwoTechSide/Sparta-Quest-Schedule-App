package com.scheduleapp.exception;

import com.scheduleapp.dto.ErrorResponseDto;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.DataTruncation;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomException(CustomException e) {
        HttpStatus httpStatus = e.getErrorCode().getHttpStatus();
        String message = e.getErrorCode().getMessage();

        return new ResponseEntity<>(new ErrorResponseDto(httpStatus, message), httpStatus);
    }

    @ExceptionHandler({ DataTruncation.class, PropertyValueException.class })
    public ResponseEntity<ErrorResponseDto> handleDataTruncation() {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String message = "too long or nullable data request";

        return new ResponseEntity<>(new ErrorResponseDto(httpStatus, message), httpStatus);
    }
}
