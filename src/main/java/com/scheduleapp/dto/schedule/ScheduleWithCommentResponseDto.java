package com.scheduleapp.dto.schedule;

import com.scheduleapp.dto.comment.CommentResponseDto;
import com.scheduleapp.entity.Comment;
import com.scheduleapp.entity.Schedule;
import lombok.Getter;

import java.util.List;

@Getter
public class ScheduleWithCommentResponseDto {

    private ScheduleResponseDto schedule;
    private List<CommentResponseDto> comments;

    public ScheduleWithCommentResponseDto(Schedule schedule, List<Comment> comments) {

        this.schedule = new ScheduleResponseDto(schedule);
        this.comments = comments.stream()
                .map(CommentResponseDto::new)
                .toList();
    }
}
