package com.pl.edu.wut.master.thesis.bug.model.jiracomment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MediaSingleContent extends ContentItem {
    private MediaSingleAttrs attrs;
    private List<MediaObject> content; // CHANGED: Now expects List<MediaObject>

    public MediaSingleContent() {
        super("mediaSingle");
    }

    public MediaSingleContent(MediaSingleAttrs attrs, List<MediaObject> content) {
        super("mediaSingle");
        this.attrs = attrs;
        this.content = content;
    }
}
