package com.pl.edu.wut.master.thesis.bug.dto.comment;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ContentItem {
    private String type;
    private List<ContentElement> content;
}