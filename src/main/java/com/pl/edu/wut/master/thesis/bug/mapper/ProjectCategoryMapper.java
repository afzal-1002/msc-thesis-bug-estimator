package com.pl.edu.wut.master.thesis.bug.mapper;

import com.pl.edu.wut.master.thesis.bug.dto.response.ProjectCategoryResponse;
import com.pl.edu.wut.master.thesis.bug.model.project.ProjectCategory;
import org.springframework.stereotype.Component;

@Component
public class ProjectCategoryMapper {

    public ProjectCategory toEntity(ProjectCategoryResponse dto) {
        if (dto == null) {
            return null;
        }

        return ProjectCategory.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public ProjectCategoryResponse toResponse(ProjectCategory entity) {
        if (entity == null) {
            return null;
        }

        return ProjectCategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }


    public  ProjectCategoryResponse mapToResponse(ProjectCategory category) {
        if (category == null) {
            return null;
        }
        return ProjectCategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}