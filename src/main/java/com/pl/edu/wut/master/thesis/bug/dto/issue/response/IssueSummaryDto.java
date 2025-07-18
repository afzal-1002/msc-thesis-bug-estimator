package com.pl.edu.wut.master.thesis.bug.dto.issue.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueSummaryDto {
    private String key;
    private String summary;
    private String status; // Remains String for display
}