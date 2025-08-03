package com.scheduleapp.controller;

import com.scheduleapp.dto.comment.CommentRequestDto;
import com.scheduleapp.dto.comment.CommentResponseDto;
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
    public ResponseEntity<CommentRequestDto> createComment(@PathVariable Long scheduleId, @RequestBody CommentResponseDto commentResponseDto) {

        CommentRequestDto savedComment = commentService.saveComment(commentResponseDto, scheduleId);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }
}
