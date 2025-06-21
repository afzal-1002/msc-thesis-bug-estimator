
package com.pl.edu.wut.master.thesis.bug.service;

import com.pl.edu.wut.master.thesis.bug.dto.request.ComponentInfoRequest;
import com.pl.edu.wut.master.thesis.bug.dto.response.ComponentInfoResponse;

import java.util.List;

public interface ComponentService {
    List<ComponentInfoResponse> getAllComponents();
    ComponentInfoResponse getComponentById(Long componentId);
    List<ComponentInfoResponse> getComponentsForProject(String projectKey);
    ComponentInfoResponse createComponent(String projectKey, ComponentInfoRequest request);
    ComponentInfoResponse updateComponent(Long componentId, ComponentInfoRequest request);
    void deleteComponent(Long componentId);
}
