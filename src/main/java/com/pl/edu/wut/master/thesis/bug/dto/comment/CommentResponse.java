// com/pl/edu/wut/master/thesis/bug/dto/comment/CommentResponse.java
package com.pl.edu.wut.master.thesis.bug.dto.comment;

import com.fasterxml.jackson.databind.JsonNode;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import lombok.*;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {
    private String            self;           // the JIRA link
    private String            id;             // e.g. "10132"
    private UserWithAvatars   author;         // extended summary with avatarUrls
    private JsonNode          body;           // full ADF, including mediaSingle/status
    private UserWithAvatars   updateAuthor;
    private OffsetDateTime    created;        // "2025-07-11T12:21:06.019+0200"
    private OffsetDateTime    updated;
    private Boolean           jsdPublic;      // true or false

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserWithAvatars {
        private String                      self;
        private String                      accountId;
        private String                      emailAddress;
        private String                      displayName;
        private Boolean                     active;
        private String                      timeZone;
        private String                      accountType;
        private AvatarUrls                  avatarUrls;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AvatarUrls {
        private String _48x48;
        private String _24x24;
        private String _16x16;
        private String _32x32;
    }
}
