
package com.pl.edu.wut.master.thesis.bug.mapper;

import com.pl.edu.wut.master.thesis.bug.dto.request.ComponentInfoRequest;
import com.pl.edu.wut.master.thesis.bug.dto.response.ComponentInfoResponse;
import com.pl.edu.wut.master.thesis.bug.model.component.ComponentInfo;
import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ComponentInfoMapper {

    private final UserSummaryMapper userSummaryMapper;

    public ComponentInfo toEntity(ComponentInfoRequest dto) {
        if (dto == null) return null;
        Project project = dto.getProjectId() != null ? Project.builder().id(dto.getProjectId()).build() : null;

        ComponentInfo.ComponentInfoBuilder builder = ComponentInfo.builder();
        builder.id(null);
        builder.jiraComponentId(dto.getJiraComponentId());
        builder.name(dto.getName());
        builder.description(dto.getDescription());
        builder.lead(userSummaryMapper.toEntity(dto.getLead()));
        builder.project(project);
        return builder.build();
    }

    public ComponentInfoResponse toResponse(ComponentInfo entity) {
        if (entity == null) return null;
        ComponentInfoResponse.ComponentInfoResponseBuilder builder = ComponentInfoResponse.builder();
        builder.id(entity.getId());
        builder.jiraComponentId(entity.getJiraComponentId());
        builder.name(entity.getName());
        builder.description(entity.getDescription());
        builder.lead(userSummaryMapper.toResponse(entity.getLead()));
        builder.projectId(entity.getProject() != null ? entity.getProject().getId() : null);
        return builder.build();
    }
}
