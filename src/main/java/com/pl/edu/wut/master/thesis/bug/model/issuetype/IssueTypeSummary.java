package com.pl.edu.wut.master.thesis.bug.model.issuetype;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class IssueTypeSummary {
    private String self;
    private String id;
    private String name;
    private String untranslatedName;
    private String description;
    private String iconUrl;
    private boolean subtask;
    private Integer avatarId;
    private int hierarchyLevel;

    private Scope scope;

    @Data
    @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Scope {
        private String type;
        private Project project;

        @Data
        @NoArgsConstructor @AllArgsConstructor @Builder
        public static class Project {
            private String id;
        }
    }
}
