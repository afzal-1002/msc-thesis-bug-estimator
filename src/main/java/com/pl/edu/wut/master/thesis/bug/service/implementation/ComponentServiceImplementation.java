//package com.pl.edu.wut.master.thesis.bug.service.implementation;
//
//import com.pl.edu.wut.master.thesis.bug.dto.request.ComponentInfoRequest;
//import com.pl.edu.wut.master.thesis.bug.dto.response.ComponentInfoResponse;
//import com.pl.edu.wut.master.thesis.bug.repository.ComponentRepository;
//import com.pl.edu.wut.master.thesis.bug.service.ComponentService;
//import com.pl.edu.wut.master.thesis.bug.service.JiraClientService;
//import com.pl.edu.wut.master.thesis.bug.mapper.ComponentInfoMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class ComponentServiceImplementation implements ComponentService {
//
//    private final ComponentRepository componentRepository;
//    private final ComponentInfoMapper componentInfoMapper;
//    private final JiraClientService jiraClientService;
//
//    @Override
//    public List<ComponentInfoResponse> getAllComponents() {
//        throw new UnsupportedOperationException("Not implemented");
//    }
//
//    @Override
//    public ComponentInfoResponse getComponentById(Long componentId) {
//        throw new UnsupportedOperationException("Not implemented");
//    }
//
//    @Override
//    public List<ComponentInfoResponse> getComponentsForProject(Long projectId) {
//        throw new UnsupportedOperationException("Not implemented");
//    }
//
//    @Override
//    public ComponentInfoResponse addComponentToProject(ComponentInfoRequest request) {
//        throw new UnsupportedOperationException("Not implemented");
//    }
//
//    @Override
//    public ComponentInfoResponse updateComponent(Long componentId, ComponentInfoRequest request) {
//        throw new UnsupportedOperationException("Not implemented");
//    }
//
//    @Override
//    public void deleteComponent(Long componentId) {
//        throw new UnsupportedOperationException("Not implemented");
//    }
//}
