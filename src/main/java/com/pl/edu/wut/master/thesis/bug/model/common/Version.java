package com.pl.edu.wut.master.thesis.bug.model.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Version {
    private String self;
    private String id;
    private String description;
    private String name;
    private boolean archived;
    private boolean released;
    private String releaseDate;
    private String userReleaseDate;
    private String projectId;
    private String startDate;
    private String userStartDate;

}




