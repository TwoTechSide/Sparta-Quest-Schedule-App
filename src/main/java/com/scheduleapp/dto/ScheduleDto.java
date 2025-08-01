package com.scheduleapp.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ScheduleDto {
    private Long id;

    private String title;
    private String content;
    private String userName;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
