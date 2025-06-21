//package com.pl.edu.wut.master.thesis.bug.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/bugs")
//@RequiredArgsConstructor
//public class BugController {
//
//    private final BugService bugService;
//
//    /** Return list of all bugs as JSON array */
//    @GetMapping
//    public ResponseEntity<List<BugResponse>> getAllBugs() {
//        List<BugResponse> bugList = bugService.getAllBugs();
//        return ResponseEntity.ok(bugList);
//    }
//
//    /** Get one issue by its issueKey */
//    @GetMapping("/{key}")
//    public ResponseEntity<BugResponse> getBugByKey(@PathVariable String key) {
//        BugResponse bug = bugService.getBugByKey(key);
//        return ResponseEntity.ok(bug);
//    }
//
//    /** Create a new issue */
//    @PostMapping
//    public ResponseEntity<BugResponse> createBug(@RequestBody IssueReq dto) {
//        BugResponse created = bugService.createBug(dto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(created);
//    }
//
//    /** Delete a issue by its database ID */
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteBug(@PathVariable Long id) {
//        bugService.deleteBug(id);
//    }
//}
