
package com.pl.edu.wut.master.thesis.bug.service;

import com.pl.edu.wut.master.thesis.bug.dto.request.VersionRequest;
import com.pl.edu.wut.master.thesis.bug.dto.response.VersionResponse;

import java.util.List;

public interface VersionService {
    List<VersionResponse> getAllVersions();
    VersionResponse getVersionById(Long versionId);
    List<VersionResponse> getVersionsForProject(String projectKey);
    VersionResponse createVersion(String projectKey, VersionRequest request);
    VersionResponse updateVersion(Long versionId, VersionRequest request);
    void deleteVersion(Long versionId);
}
