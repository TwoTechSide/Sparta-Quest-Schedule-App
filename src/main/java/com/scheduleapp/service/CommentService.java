package com.scheduleapp.service;

import com.scheduleapp.dto.CommentDto;
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
    public CommentDto saveComment(CommentDto commentDto, Long scheduleId) {

        long commentsCount = commentRepository.countByScheduleId(scheduleId);
        if (commentsCount >= 10) {
            throw new CustomException(ErrorCode.COMMENT_LIMIT_EXCEEDED);
        }

        Schedule schedule = scheduleService.findScheduleByIdOrThrow(scheduleId);
        Comment newComment = new Comment(commentDto, schedule);

        commentRepository.save(newComment);

        return commentDto;
    }
}
