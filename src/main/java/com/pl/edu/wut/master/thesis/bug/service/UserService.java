package com.pl.edu.wut.master.thesis.bug.service;

import com.pl.edu.wut.master.thesis.bug.dto.user.*;
import com.pl.edu.wut.master.thesis.bug.model.user.UserReference;
import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import com.pl.edu.wut.master.thesis.bug.model.user.User;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public UserSummary registerUser(RegisterUserRequest request);
    public UserSummary   getUserById(Long id);

    Optional<User> find(UserReference reference);

    public User findUserById(Long id);
    public List<UserSummary> getAllUsers();
    public UserSummary  updateUser(Long id, UserRequest request);
    public UserSummary  updateUser(User userInput);
    public UserSummary  findUserByAccountId(String accountId);
    public User findUserEntityByAccountId(String accountId);
    public UserSummary getUserSummaryByAccountId(String accountId);
    public UserSummary   findByEmailAddress(String email);
    public List<Project> getProjectsForUser(Long userId);
    public UserSummary  userLogin(LoginRequest request, HttpSession session);
    public User saveAndFlushUser(User user);
    public User findUserByIdWithProjects(Long userId);

    boolean existsByUsername(String username);
    User getCurrentUser(HttpSession session);
    Optional<User> findByUsername(String username);

    Optional<User> findById(String accountId);
    Optional<User> findByUserName(String userName);

}