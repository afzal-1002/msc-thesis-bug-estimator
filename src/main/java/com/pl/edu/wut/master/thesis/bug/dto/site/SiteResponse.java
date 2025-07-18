package com.pl.edu.wut.master.thesis.bug.dto.site;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SiteResponse {
    private Long   siteId;
    private String sitename;
    private String baseUrl;

    private String loginName;
    private String username;



}
