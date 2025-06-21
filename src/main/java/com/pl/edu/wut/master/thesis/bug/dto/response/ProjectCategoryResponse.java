package com.pl.edu.wut.master.thesis.bug.dto.response;

import com.pl.edu.wut.master.thesis.bug.model.project.ProjectCategory;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCategoryResponse {
    private Long id;
    private String name;
    private String description;
}