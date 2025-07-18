package com.pl.edu.wut.master.thesis.bug.model.jiracomment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusContent extends ParagraphChild {
    private StatusAttrs attrs;

    public StatusContent() {
        super("status");
    }

    public StatusContent(StatusAttrs attrs) {
        super("status");
        this.attrs = attrs;
    }
}