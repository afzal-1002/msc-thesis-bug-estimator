package com.pl.edu.wut.master.thesis.bug.dto.issue.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Response from GET /rest/api/3/issue/{issueIdOrKey}/changelog
 *
 * Example JSON snippet:
 * {
 *   "isLast": false,
 *   "maxResults": 2,
 *   "nextPage": "...",
 *   "self": "...",
 *   "startAt": 2,
 *   "total": 5,
 *   "values": [ { "id": "10001", "author": { ... }, "created": "...", "items": [ ... ] }, ... ]
 * }
 */
@Data
public class ChangelogResponse {
    @JsonProperty("isLast")
    private boolean isLast;
    private int maxResults;
    private String nextPage;
    private String self;
    private int startAt;
    private int total;
    private List<ChangelogItem> values;

    @Data
    public static class ChangelogItem {
        private String id;
        private Author author;
        private String created;
        private List<Item> items;
    }

    @Data
    public static class Author {
        private String accountId;
        private boolean active;
        private Map<String, String> avatarUrls;
        private String displayName;
        private String emailAddress;
        private String self;
        private String timeZone;
    }

    @Data
    public static class Item {
        private String field;
        private String fieldtype;
        private String fieldId;
        private String from;
        private String fromString;
        private String to;
        private String toString;
    }
}
