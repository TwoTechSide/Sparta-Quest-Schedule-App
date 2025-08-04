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

    @Column(nullable = false, length = 30)
    private String title;
    @Column(nullable = false, length = 200)
    private String content;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule")
    private List<Comment> comments;

    public void updateTitleAndUsername(String title, String username) {
        this.title = title;
        this.userName = username;
    }
}