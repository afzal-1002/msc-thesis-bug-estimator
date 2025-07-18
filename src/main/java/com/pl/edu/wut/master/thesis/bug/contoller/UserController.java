package com.pl.edu.wut.master.thesis.bug.contoller;

import com.pl.edu.wut.master.thesis.bug.dto.user.*;
import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import com.pl.edu.wut.master.thesis.bug.model.user.User;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import com.pl.edu.wut.master.thesis.bug.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wut/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserSummary> registerUser(@RequestBody RegisterUserRequest request) {
        UserSummary resp = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<UserSummary> userLogin(@RequestBody LoginRequest request, HttpSession session) {
        UserSummary userSummary =  userService.userLogin(request, session);
        return ResponseEntity.ok(userSummary);
    }

    @GetMapping("/by-email")
    public ResponseEntity<UserSummary> getByEmail(@RequestParam("email") String email) {
        log.info("GET /api/users/by-email?email={}", email);
        UserSummary user = userService.findByEmailAddress(email);
        if (user == null) { return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(user);
    }



    @GetMapping("/{id}")
    public ResponseEntity<UserSummary> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserSummary>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserSummary> updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @GetMapping("/{userId}/projects")
    public ResponseEntity<List<Project>> getProjectsForUser(@PathVariable Long userId) {
        List<Project> projects = userService.getProjectsForUser(userId);
        return ResponseEntity.ok(projects);
    }

}