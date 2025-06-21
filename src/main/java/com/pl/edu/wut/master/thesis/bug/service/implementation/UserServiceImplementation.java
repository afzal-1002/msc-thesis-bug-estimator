package com.pl.edu.wut.master.thesis.bug.service.implementation;

import com.pl.edu.wut.master.thesis.bug.dto.response.ProjectResponse;
import com.pl.edu.wut.master.thesis.bug.dto.response.UserResponse;
import com.pl.edu.wut.master.thesis.bug.exception.ResourceNotFoundException;
import com.pl.edu.wut.master.thesis.bug.mapper.ProjectMapper;
import com.pl.edu.wut.master.thesis.bug.mapper.UserMapper;
import com.pl.edu.wut.master.thesis.bug.model.user.User;
import com.pl.edu.wut.master.thesis.bug.repository.UserRepository;
import com.pl.edu.wut.master.thesis.bug.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectMapper projectMapper;

    @Override
    public User createUser(User user) {
        // Validate required fields
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        // In production: Encrypt password before saving
        if (user.getAccountId() == null) {
            user.setAccountId(UUID.randomUUID().toString());
        }

        return userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toUserResponseWithProjects)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserByIdResponse(Long id) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        return toUserResponseWithProjects(u);
    }

    private UserResponse toUserResponseWithProjects(User u) {
        // Initialize projects and inner project.roles while we're still in the transaction
        List<ProjectResponse> projects = u.getProjects().stream()
                .map(projectMapper::toResponse)
                .collect(Collectors.toList());

        return UserResponse.builder()
                .id(u.getId())
                .accountId(u.getAccountId())
                .username(u.getUsername())
                .email(u.getEmail())
                .projects(projects)
                .build();
    }


    @Override
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        User user = this.userRepository.findByEmail(username).orElse(null);
        if (user == null) {
            user = this.userRepository.findByUsername(username).orElse(null);
        }
        return user;
    }


    @Override
    public User updateUser(User user){
        User user1 = this.findUserById(user.getId());
        user1.setEmail(user.getEmail());
        user1.setAccountId(user.getAccountId());
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword());
        return userRepository.save(user1);
    }



}

