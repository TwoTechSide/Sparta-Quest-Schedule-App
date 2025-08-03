package com.scheduleapp.exception;

public class InvalidPasswordException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Invalid Password";
    public InvalidPasswordException() {
        super(ERROR_MESSAGE);
    }
}
