package com.scheduleapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String userName;
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule")
    private List<Comment> comments;

    public void updateTitleAndUsername(String title, String username) {
        this.title = title;
        this.userName = username;
    }
}