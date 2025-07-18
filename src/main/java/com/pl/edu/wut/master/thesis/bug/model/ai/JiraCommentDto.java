package com.pl.edu.wut.master.thesis.bug.model.ai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class JiraCommentDto {
    private String id;
    private String author;
    private String body;
    private OffsetDateTime created;

}