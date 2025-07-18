package com.pl.edu.wut.master.thesis.bug.model.jiracomment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParagraphContent extends ContentItem {
    private List<ParagraphChild> content;

    public ParagraphContent() {
        super("paragraph");
    }

    public ParagraphContent(List<ParagraphChild> content) {
        super("paragraph");
        this.content = content;
    }
}