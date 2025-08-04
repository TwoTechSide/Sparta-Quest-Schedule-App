package com.scheduleapp.dto.comment;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long id;

    private String content;
    private String userName;
    private String password;
}
