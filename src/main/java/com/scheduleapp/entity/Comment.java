package com.scheduleapp.entity;

import com.scheduleapp.dto.comment.CommentRequestDto;
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

    public Comment(CommentRequestDto commentRequestDto, Schedule schedule) {
        this.content = commentRequestDto.getContent();
        this.userName = commentRequestDto.getUserName();
        this.password = commentRequestDto.getPassword();
        this.schedule = schedule;
    }

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
}
