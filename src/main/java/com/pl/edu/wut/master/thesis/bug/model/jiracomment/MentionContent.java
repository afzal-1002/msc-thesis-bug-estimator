package com.pl.edu.wut.master.thesis.bug.model.jiracomment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MentionContent extends ParagraphChild {
    private MentionAttrs attrs;

    public MentionContent() {
        super("mention");
    }

    public MentionContent(MentionAttrs attrs) {
        super("mention");
        this.attrs = attrs;
    }
}
