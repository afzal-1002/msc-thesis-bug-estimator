package com.pl.edu.wut.master.thesis.bug.dto.project;


import com.pl.edu.wut.master.thesis.bug.model.common.IssueType;
import com.pl.edu.wut.master.thesis.bug.model.common.AvatarUrls;
import com.pl.edu.wut.master.thesis.bug.model.common.Version;
import com.pl.edu.wut.master.thesis.bug.model.component.ComponentSummary;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import lombok.*;

import java.util.List;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProjectDetailsSummary {
    private String expand;
    private String self;
    private String id;
    private String key;
    private String description;
    private UserSummary lead;
    private List<ComponentSummary> components;
    private List<IssueType> issueTypes;
    private String assigneeType;
    private List<Version> versions;
    private String name;
    private Map<String, String> roles;
    private AvatarUrls avatarUrls;
    private String projectTypeKey;
    private boolean simplified;
    private String style;
    private boolean isPrivate;
    private Map<String, Object> properties;
}
