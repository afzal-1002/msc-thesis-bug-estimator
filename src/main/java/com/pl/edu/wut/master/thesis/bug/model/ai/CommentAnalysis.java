package com.pl.edu.wut.master.thesis.bug.model.ai;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment_analysis")
@Data
public class CommentAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String issueKey;

    @Lob
    @Column(nullable = false)
    private String originalComment;

    @Lob
    @Column(nullable = false)
    private String aiAnalysis;

    @Column(nullable = false)
    private LocalDateTime analysisTimestamp;

}