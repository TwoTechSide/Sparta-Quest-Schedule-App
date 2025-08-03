package com.scheduleapp.controller;

import com.scheduleapp.dto.CommentDto;
import com.scheduleapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class CommentController {

    private final CommentService commentService;

    // 댓글 추가
    @PostMapping("/{scheduleId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long scheduleId, @RequestBody CommentDto commentDto) {
        CommentDto commentDtoContainScheduleId = commentDto.toBuilder()
                .ScheduleId(scheduleId).build();

        CommentDto savedComment = commentService.saveComment(commentDtoContainScheduleId, scheduleId);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }
}
