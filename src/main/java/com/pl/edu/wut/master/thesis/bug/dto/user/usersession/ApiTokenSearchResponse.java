package com.pl.edu.wut.master.thesis.bug.dto.user.usersession;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Data
@Getter
@Setter
public class ApiTokenSearchResponse {
    private List<ApiTokenInfo> data;
    private Map<String,String> links;
}
