package com.pl.edu.wut.master.thesis.bug.model.jiracomment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MediaContent extends ContentItem {
    private MediaAttrs attrs;

    public MediaContent() {
        super("media");
    }

    public MediaContent(MediaAttrs attrs) {
        super("media");
        this.attrs = attrs;
    }
}
