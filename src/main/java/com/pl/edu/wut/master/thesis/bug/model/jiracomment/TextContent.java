package com.pl.edu.wut.master.thesis.bug.model.jiracomment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TextContent extends ParagraphChild {
    private String text;

    public TextContent() {
        super("text");
    }

    public TextContent(String text) {
        super("text");
        this.text = text;
    }
}
