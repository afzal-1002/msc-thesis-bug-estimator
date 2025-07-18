package com.pl.edu.wut.master.thesis.bug.model.ai;


import lombok.*;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiEstimationDto {
    private String  issueKey;
    private Double  estimatedTime;
    private Double  confidence;
    private OffsetDateTime createdAt;
    private String  notes;
}
