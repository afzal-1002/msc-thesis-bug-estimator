package com.pl.edu.wut.master.thesis.bug.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Priority {
    private String self;
    private String iconUrl;
    private String name;
    private String id;
}
