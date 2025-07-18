package com.pl.edu.wut.master.thesis.bug.model.jiracomment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmojiContent extends ParagraphChild {
    private EmojiAttrs attrs;

    public EmojiContent() {
        super("emoji");
    }

    public EmojiContent(EmojiAttrs attrs) {
        super("emoji");
        this.attrs = attrs;
    }
}