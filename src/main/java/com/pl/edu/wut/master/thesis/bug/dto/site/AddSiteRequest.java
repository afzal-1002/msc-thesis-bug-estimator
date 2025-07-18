package com.pl.edu.wut.master.thesis.bug.dto.site;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddSiteRequest {
    @NotBlank
    private String sitename;
}
