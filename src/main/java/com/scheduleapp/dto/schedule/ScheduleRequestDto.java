package com.scheduleapp.dto.schedule;

import com.scheduleapp.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private String title;
    private String content;
    private String userName;
    private String password;

    public Schedule toEntity() {
        return Schedule.builder()
                .title(title)
                .content(content)
                .userName(userName)
                .password(password)
                .build();
    }
}
