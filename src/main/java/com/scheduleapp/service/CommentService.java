package com.scheduleapp.service;

import com.scheduleapp.dto.comment.CommentResponseDto;
import com.scheduleapp.dto.comment.CommentRequestDto;
import com.scheduleapp.entity.Comment;
import com.scheduleapp.entity.Schedule;
import com.scheduleapp.exception.CustomException;
import com.scheduleapp.exception.ErrorCode;
import com.scheduleapp.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleService scheduleService;

    // 새로운 댓글 생성
    @Transactional
    public CommentResponseDto saveComment(CommentRequestDto commentRequestDto, Long scheduleId) {

        long commentsCount = commentRepository.countByScheduleId(scheduleId);
        if (commentsCount >= 10) {
            throw new CustomException(ErrorCode.COMMENT_LIMIT_EXCEEDED);
        }

        Schedule schedule = scheduleService.findScheduleByIdOrThrow(scheduleId);
        Comment comment = new Comment(commentRequestDto, schedule);

        commentRepository.save(comment);

        return entityToDto(comment);
    }

    public CommentResponseDto entityToDto(Comment comment) {
        return CommentResponseDto.builder()
                .content(comment.getContent())
                .userName(comment.getUserName())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt()).build();
    }
}
