package com.pl.edu.wut.master.thesis.bug.model.common;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Document {
    private String type;
    private int version;
    private List<Paragraph> content;

    @Data
    @NoArgsConstructor @AllArgsConstructor @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Paragraph {
        private String type;
        private List<ContentItem> content;

        @Data
        @NoArgsConstructor @AllArgsConstructor @Builder
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class ContentItem {
            private String type;
            private String text;
        }
    }
}