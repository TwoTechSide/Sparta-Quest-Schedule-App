package com.scheduleapp.dto;

import com.scheduleapp.entity.Schedule;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class CommentDto {
    private Long id;

    private String content;
    private String userName;
    private String password;

    private Long ScheduleId;
}
