package com.scheduleapp.exception;

public class ScheduleNotFoundException extends RuntimeException {
    public static final String ERROR_MESSAGE = "Schedule not found";
    public ScheduleNotFoundException() { super(ERROR_MESSAGE); }
}