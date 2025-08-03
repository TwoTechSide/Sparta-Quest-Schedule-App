package com.scheduleapp.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private String content;
    private String userName;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
