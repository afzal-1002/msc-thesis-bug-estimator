package com.pl.edu.wut.master.thesis.bug.model.jiracomment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Base class for different content types in the 'content' array
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ParagraphContent.class, name = "paragraph"),
        @JsonSubTypes.Type(value = EmojiContent.class, name = "emoji"),
        @JsonSubTypes.Type(value = MentionContent.class, name = "mention"),
        @JsonSubTypes.Type(value = StatusContent.class, name = "status"),
        @JsonSubTypes.Type(value = MediaSingleContent.class, name = "mediaSingle"),
        @JsonSubTypes.Type(value = TextContent.class, name = "text")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ContentItem {
    private String type;
}
