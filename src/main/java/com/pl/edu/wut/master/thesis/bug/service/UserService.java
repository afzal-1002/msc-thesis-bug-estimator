package com.pl.edu.wut.master.thesis.bug.service;

import com.pl.edu.wut.master.thesis.bug.dto.response.UserResponse;
import com.pl.edu.wut.master.thesis.bug.model.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public User createUser(User user);
    public User findUserById(Long id);
    public List<UserResponse> getAllUsers();
    public User findByEmail(String email);
    public User findByUsername(String username);
    public User updateUser(User user);

    UserResponse getUserByIdResponse(Long id);
}