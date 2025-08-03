package com.scheduleapp.exception;

public class ScheduleNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Schedule not found";
    public ScheduleNotFoundException() { super(ERROR_MESSAGE); }
}