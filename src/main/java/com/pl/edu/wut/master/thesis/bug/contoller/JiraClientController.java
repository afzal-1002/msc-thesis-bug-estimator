//package com.pl.edu.wut.master.thesis.bug.contoller;
//
//
//import com.pl.edu.wut.master.thesis.bug.dto.request.ImportProjectRequest;
//import com.pl.edu.wut.master.thesis.bug.service.JiraClientService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/jira")
//@RequiredArgsConstructor
//public class JiraClientController {
//
//    private final JiraClientService jiraClientService;
//
//    @PostMapping("/project")
//    public ResponseEntity<String> getProject(@RequestBody ImportProjectRequest request) {
//        String result = jiraClientService.fetchProject(request);
//        return ResponseEntity.ok(result);
//    }
//}