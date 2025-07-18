package com.pl.edu.wut.master.thesis.bug.mapper;

import com.pl.edu.wut.master.thesis.bug.dto.project.CreateProjectRequest;
import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectResponse;
import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectSummary;
import com.pl.edu.wut.master.thesis.bug.model.common.AvatarUrls;
import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);


    Project toProjectEntity(CreateProjectRequest request);


    Project toProjectEntity(Project src);


    @Mapping(target = "id", ignore = true)
    void updateProjectFromEntity(Project src, @MappingTarget Project dest);


    @Mapping(source = "key",            target = "key")
    @Mapping(source = "id",             target = "id")
    @Mapping(source = "name",           target = "name")
    @Mapping(source = "description",    target = "description")
    @Mapping(source = "baseUrl",        target = "baseUrl")
    @Mapping(source = "projectTypeKey", target = "projectTypeKey")
    @Mapping(source = "lead",           target = "lead")
    @Mapping(source = "users",          target = "users")
    @Mapping(source = "issues",         target = "issues")
    ProjectResponse fromProjectToResponse(Project project);

    /**
     * If you ever need to go from response → entity
     */
    Project fromResponseToEntity(ProjectResponse response);

    // In ProjectMapper interface
    @Mapping(target = "users", ignore = true) // Ignore collections if needed
    @Mapping(target = "issues", ignore = true)
    Project toProjectEntity(ProjectResponse projectResponse);


    @Mapping(target = "avatarUrl", source = "avatarUrls")
    Project toProjectEntity(ProjectSummary summary);


    default AvatarUrls map(AvatarUrls source) {
        if (source == null) {
            return null;
        }
        AvatarUrls avatarUrls = new AvatarUrls();
        avatarUrls.setUrl16x16(source.getUrl16x16());
        avatarUrls.setUrl24x24(source.getUrl24x24());
        avatarUrls.setUrl32x32(source.getUrl32x32());
        avatarUrls.setUrl48x48(source.getUrl48x48());
        return avatarUrls;
    }
}
