package com.pl.edu.wut.master.thesis.bug.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Description {
    private String           type;
    private int              version;
    private List<Content> content;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Content {
        private String type;
        private List<ContentItem> content;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public  static class ContentItem {
            private String text;
            private String type;
            private List<Mark> marks;

            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Mark {
                private String type;
                private Map<String,Object> attrs;
            }
        }

    }

}
