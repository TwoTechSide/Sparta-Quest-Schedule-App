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

    @Column(nullable = false, length = 100)
    private String content;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
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
