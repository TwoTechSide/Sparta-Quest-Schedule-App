package com.scheduleapp.dto.schedule;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ScheduleResponseDto {
    private Long id;

    private String title;
    private String content;
    private String userName;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
