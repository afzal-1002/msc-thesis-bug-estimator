//package com.pl.edu.wut.master.thesis.bug.service.implementation;
//
//import com.pl.edu.wut.master.thesis.bug.dto.request.VersionRequest;
//import com.pl.edu.wut.master.thesis.bug.dto.response.VersionResponse;
//import com.pl.edu.wut.master.thesis.bug.repository.VersionRepository;
//import com.pl.edu.wut.master.thesis.bug.service.VersionService;
//import com.pl.edu.wut.master.thesis.bug.service.JiraClientService;
//import com.pl.edu.wut.master.thesis.bug.mapper.VersionMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class VersionServiceImplementation implements VersionService {
//
//    private final VersionRepository versionRepository;
//    private final VersionMapper versionMapper;
//    private final JiraClientService jiraClientService;
//
//    @Override
//    public List<VersionResponse> getAllVersions() {
//        throw new UnsupportedOperationException("Not implemented");
//    }
//
//    @Override
//    public VersionResponse getVersionById(Long versionId) {
//        throw new UnsupportedOperationException("Not implemented");
//    }
//
//    @Override
//    public List<VersionResponse> getVersionsForProject(Long projectId) {
//        throw new UnsupportedOperationException("Not implemented");
//    }
//
//    @Override
//    public VersionResponse addVersionToProject(VersionRequest request) {
//        throw new UnsupportedOperationException(" Not implemented");
//    }
//
//    @Override
//    public VersionResponse updateVersion(Long versionId, VersionRequest request) {
//        throw new UnsupportedOperationException("Not implemented");
//    }
//
//    @Override
//    public void deleteVersion(Long versionId) {
//        throw new UnsupportedOperationException("Not implemented");
//    }
//}
