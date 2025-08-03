package com.scheduleapp.entity;

import com.scheduleapp.dto.comment.CommentResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String userName;
    private String password;

    public Comment(CommentResponseDto commentResponseDto, Schedule schedule) {
        this.content = commentResponseDto.getContent();
        this.userName = commentResponseDto.getUserName();
        this.password = commentResponseDto.getPassword();
        this.schedule = schedule;
    }

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
}
