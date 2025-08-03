package com.scheduleapp.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentDto {
    private Long id;

    private String content;
    private String userName;
    private String password;
}
