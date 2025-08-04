package com.scheduleapp.controller;

import com.scheduleapp.dto.comment.CommentResponseDto;
import com.scheduleapp.dto.comment.CommentRequestDto;
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
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long scheduleId, @RequestBody CommentRequestDto commentRequestDto) {

        CommentResponseDto savedComment = commentService.saveComment(commentRequestDto, scheduleId);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }
}
