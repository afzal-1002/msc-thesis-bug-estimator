package com.pl.edu.wut.master.thesis.bug.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public  class UpdateSiteRequest {
    @NotBlank String sitename;
    @NotBlank String baseUrl;
}
