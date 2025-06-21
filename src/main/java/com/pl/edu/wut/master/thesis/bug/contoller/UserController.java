// com/pl/edu/wut/master/thesis/bug/controller/UserController.java
package com.pl.edu.wut.master.thesis.bug.controller;

import com.pl.edu.wut.master.thesis.bug.dto.request.UserRequest;
import com.pl.edu.wut.master.thesis.bug.dto.response.UserResponse;
import com.pl.edu.wut.master.thesis.bug.mapper.UserMapper;
import com.pl.edu.wut.master.thesis.bug.model.user.User;
import com.pl.edu.wut.master.thesis.bug.dto.response.ProjectResponse;
import com.pl.edu.wut.master.thesis.bug.service.ProjectService;
import com.pl.edu.wut.master.thesis.bug.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponse> createUser( @RequestBody UserRequest req) {
        User u = userMapper.toEntity(req);
        User saved = userService.createUser(u);
        return ResponseEntity.ok(userMapper.toResponse(saved));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(
                userService.getUserByIdResponse(userId)
        );
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}/projects")
    public ResponseEntity<List<ProjectResponse>> getProjectsForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserByIdResponse(userId).getProjects());
    }
}