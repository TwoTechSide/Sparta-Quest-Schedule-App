package com.scheduleapp.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScheduleDto {
    private Long id;

    private String title;
    private String content;
    private String userName;
}
