package com.pl.edu.wut.master.thesis.bug.model.user;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class UserSummary {
        private String accountId;
        private String emailAddress;
        private String displayName;
}
