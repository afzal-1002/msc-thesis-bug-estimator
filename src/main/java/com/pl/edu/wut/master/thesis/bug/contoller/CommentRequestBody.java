package com.pl.edu.wut.master.thesis.bug.contoller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentRequestBody {
    private String         type;     // "doc"
    private int            version;  // 1
    private List<Block>    content;  // top‑level ADF blocks

    // Base type for all ADF blocks
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.PROPERTY,
            property = "type"
    )
    @JsonSubTypes({
            @JsonSubTypes.Type(value = Paragraph.class,    name = "paragraph"),
            @JsonSubTypes.Type(value = MediaSingle.class,  name = "mediaSingle"),
            @JsonSubTypes.Type(value = Status.class,       name = "status")
    })
    public static abstract class Block { }

    @EqualsAndHashCode(callSuper = true)
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Paragraph extends Block {
        private List<ParaContent> content;

        @Data @NoArgsConstructor @AllArgsConstructor @Builder
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ParaContent {
            private String type;   // "text" or "status"
            private String text;   // only for type="text"

            // for inline status nodes inside a paragraph
            private Status.Attrs attrs;
        }
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class MediaSingle extends Block {
        private Attrs attrs;
        private List<Media> content;

        @Data @NoArgsConstructor @AllArgsConstructor @Builder
        public static class Attrs {
            private String layout;  // e.g. "align-start"
        }
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Media {
        private String type;   // "media"
        private Attrs attrs;

        @Data @NoArgsConstructor @AllArgsConstructor @Builder
        public static class Attrs {
            private String   type;       // "file"
            private String   id;         // file ID
            private String   collection; // attachment collection
            private Integer  height;
            private Integer  width;
            private String   alt;
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Status extends Block {
        private Attrs attrs;

        @Data @NoArgsConstructor @AllArgsConstructor @Builder
        public static class Attrs {
            private String text;
            private String color;
            private String localId;
            private String style;
        }
    }
}