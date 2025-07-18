package com.pl.edu.wut.master.thesis.bug.dto.comment;

import lombok.Data;

import java.util.List;

@Data
public class CommentBody {
    private String type = "doc";
    private int version = 1;
    private List<ContentItem> content;
}