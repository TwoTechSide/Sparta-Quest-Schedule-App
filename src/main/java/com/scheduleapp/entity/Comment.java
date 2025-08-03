package com.scheduleapp.entity;

import com.scheduleapp.dto.CommentDto;
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

    public Comment(CommentDto commentDto, Schedule schedule) {
        this.content = commentDto.getContent();
        this.userName = commentDto.getUserName();
        this.password = commentDto.getPassword();
        this.schedule = schedule;
    }

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
}
