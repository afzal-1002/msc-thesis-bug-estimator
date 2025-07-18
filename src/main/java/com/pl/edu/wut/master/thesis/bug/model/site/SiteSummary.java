package com.pl.edu.wut.master.thesis.bug.model.site;


import lombok.*;

import java.time.OffsetDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SiteSummary {
    private Long id;
    private String sitename;
    private String baseUrl;
    private OffsetDateTime addedAt;
}
