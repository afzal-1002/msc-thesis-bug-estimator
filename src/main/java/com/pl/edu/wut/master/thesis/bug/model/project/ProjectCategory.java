package com.pl.edu.wut.master.thesis.bug.model.project;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProjectCategory {
    private Long id;
    private String name;
    private String description;
}
