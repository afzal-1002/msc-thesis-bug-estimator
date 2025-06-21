
package com.pl.edu.wut.master.thesis.bug.mapper;

import com.pl.edu.wut.master.thesis.bug.dto.request.VersionRequest;
import com.pl.edu.wut.master.thesis.bug.dto.response.VersionResponse;
import com.pl.edu.wut.master.thesis.bug.model.version.Version;
import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import org.springframework.stereotype.Component;

@Component
public class VersionMapper {

    public Version toEntity(VersionRequest dto) {
        if (dto == null) return null;
        Project project = dto.getProjectId() != null
                ? Project.builder().id(dto.getProjectId()).build()
                : null;

        return Version.builder()
                .id(null)
                .jiraVersionId(dto.getJiraVersionId())
                .name(dto.getName())
                .description(dto.getDescription())
                .project(project)
                .build();
    }

    public VersionResponse toResponse(Version entity) {
        if (entity == null) return null;
        return VersionResponse.builder()
                .id(entity.getId())
                .jiraVersionId(entity.getJiraVersionId())
                .name(entity.getName())
                .description(entity.getDescription())
                .projectId(entity.getProject() != null ? entity.getProject().getId() : null)
                .build();
    }
}
