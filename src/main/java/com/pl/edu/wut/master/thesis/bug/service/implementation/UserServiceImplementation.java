package com.pl.edu.wut.master.thesis.bug.service.implementation;

import com.pl.edu.wut.master.thesis.bug.dto.user.LoginRequest;
import com.pl.edu.wut.master.thesis.bug.dto.user.RegisterUserRequest;
import com.pl.edu.wut.master.thesis.bug.dto.user.UserRequest;
import com.pl.edu.wut.master.thesis.bug.model.user.*;
import com.pl.edu.wut.master.thesis.bug.exception.ResourceNotFoundException;
import com.pl.edu.wut.master.thesis.bug.exception.UserAlreadyExistException;
import com.pl.edu.wut.master.thesis.bug.exception.UserNotFoundException;
import com.pl.edu.wut.master.thesis.bug.mapper.UserMapper;
import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import com.pl.edu.wut.master.thesis.bug.model.site.Site;
import com.pl.edu.wut.master.thesis.bug.repository.SiteRepository;
import com.pl.edu.wut.master.thesis.bug.repository.UserCredentialRepository;
import com.pl.edu.wut.master.thesis.bug.repository.UserRepository;
import com.pl.edu.wut.master.thesis.bug.service.EncryptionService;
import com.pl.edu.wut.master.thesis.bug.service.JiraUserService;
import com.pl.edu.wut.master.thesis.bug.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImplementation implements UserService {

    private final UserRepository           userRepository;
    private final UserMapper               userMapper;
    private final UserCredentialRepository credentialRepository;
    private final EncryptionService        encryptionService;
    private final JiraUserService jiraUserService;
    private final SiteRepository           siteRepository;
    private final HttpSession              session;

    @Override
    @Transactional
    public UserSummary registerUser(RegisterUserRequest request) {
        // ─── Step 0: seed the HTTP session with all Jira creds ─────────
        String jiraBaseUrl = "https://" + request.getSitename() + ".atlassian.net";
        session.setAttribute("hostUrl",   jiraBaseUrl);
        session.setAttribute("loginName", request.getLoginName());
        session.setAttribute("username",  request.getUsername());
        session.setAttribute("token",     request.getToken());

        // ─── Step 1: local‐DB existence checks ───────────────────────────
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistException("Username already taken");
        }
        if (credentialRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistException("Jira account already registered");
        }

        // ─── Step 2: verify credentials & fetch Jira profile ─────────────
        JiraUserResponse profile = jiraUserService.getUserDetailsFromJira();

        // ─── Step 3: create & persist the User ───────────────────────────
        User user = User.builder()
                .logiName(request.getLoginName())
                .username(request.getUsername())
                .password(request.getPassword())
                .accountId(profile.getAccountId())
                .displayName(profile.getDisplayName())
                .emailAddress(profile.getEmailAddress())
                .isActive(profile.isActive())
                .projects(Collections.emptySet())
                .build();
        try {
            user = userRepository.saveAndFlush(user);
        } catch (DataIntegrityViolationException ex) {
            throw new UserAlreadyExistException(
                    "Registration failed: " + ex.getMostSpecificCause().getMessage()
            );
        }

        // ─── Step 4: persist the Jira credential ───────────────────────────
        UserCredential cred = UserCredential.builder()
                .user(user)
                .username(request.getUsername())
                .token(encryptionService.encrypt(request.getToken()))
                .jiraBaseUrl(jiraBaseUrl)
                .createdAt(OffsetDateTime.now())
                .expiresAt(OffsetDateTime.now().plusYears(1))
                .build();
        credentialRepository.save(cred);
        user.setJiraCredential(cred);

        // ─── Step 5: persist the initial Site ─────────────────────────────
        Site site = Site.builder()
                .credential(cred)
                .sitename(request.getSitename())
                .baseUrl(jiraBaseUrl)
                .addedAt(OffsetDateTime.now())
                .build();
        siteRepository.save(site);

        // ─── Step 6: return summary DTO ──────────────────────────────────
        return UserSummary.builder()
                .accountId(user.getAccountId())
                .emailAddress(user.getEmailAddress())
                .displayName(user.getDisplayName())
                .username(cred.getUsername())
                .active(user.isActive())
                .build();
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public UserSummary getUserById(Long id) {
        return userMapper.toUserSummary(findUserById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserSummary> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserSummary)
                .toList();
    }

    @Override
    @Transactional
    public UserSummary updateUser(Long id, UserRequest request) {
        User existing = findUserById(id);

        if (request.getUserName() != null && !request.getUserName().equals(existing.getUsername())) {
            userRepository.findByUsername(request.getUserName()).ifPresent(u ->
                    { throw new UserAlreadyExistException("Username already in use: " + request.getUserName()); }
            );
            existing.setUsername(request.getUserName());
        }
        if (request.getEmailAddress() != null && !request.getEmailAddress().equals(existing.getEmailAddress())) {
            userRepository.findByEmailAddress(request.getEmailAddress()).ifPresent(u ->
                    { throw new UserAlreadyExistException("Email already in use: " + request.getEmailAddress()); }
            );
            existing.setEmailAddress(request.getEmailAddress());
        }
        if (request.getDisplayName() != null) {
            existing.setDisplayName(request.getDisplayName());
        }
        if (request.getIsActive() != null) {
            existing.setActive(request.getIsActive());
        }
        if (request.getPassword() != null) {
            existing.setPassword(encryptionService.hashPassword(request.getPassword()));
        }

        User savedUser;
        try {
            savedUser = userRepository.saveAndFlush(existing);
        } catch (DataIntegrityViolationException ex) {
            throw new UserAlreadyExistException("Update failed: " + ex.getMostSpecificCause().getMessage());
        }
        return userMapper.toUserSummary(savedUser);
    }

    @Override
    @Transactional
    public UserSummary updateUser(User userInput) {
        if (userInput.getId() == null) {
            throw new IllegalArgumentException("User ID required");
        }
        User existingUser = findUserById(userInput.getId());
        if (userInput.getUsername() != null) existingUser.setUsername(userInput.getUsername());
        if (userInput.getEmailAddress() != null) existingUser.setEmailAddress(userInput.getEmailAddress());
        if (userInput.getDisplayName()  != null) existingUser.setDisplayName(userInput.getDisplayName());
        existingUser.setActive(userInput.isActive());
        if (userInput.getPassword()     != null) existingUser.setPassword(userInput.getPassword());

        User saved = userRepository.saveAndFlush(existingUser);
        return userMapper.toUserSummary(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UserSummary findByEmailAddress(String email) {
        return userRepository.findByEmailAddress(email)
                .map(userMapper::toUserSummary)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getProjectsForUser(Long userId) {
        return List.copyOf(findUserById(userId).getProjects());
    }

    @Override
    @Transactional
    public UserSummary userLogin(LoginRequest request, HttpSession session) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Invalid username"));

        UserCredential credential = credentialRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new IllegalStateException("No Jira credentials found"));

        session.setAttribute("loginName", credential.getUsername());
        session.setAttribute("username",  credential.getUsername());
        session.setAttribute("token",     credential.getToken());
        session.setAttribute("userId",    user.getId());
        session.setAttribute("accountId", user.getAccountId());

        return userMapper.toUserSummary(user);
    }

    @Override
    public User getCurrentUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new UserNotFoundException("You must log in first");
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found for ID " + userId));
    }

    @Override
    public Optional<User> find(UserReference reference) {
        if (reference.getId() != null) {
            return userRepository.findByAccountId(reference.getId());
        } else if (reference.getUsername() != null) {
            return userRepository.findByUsername(reference.getUsername());
        } else {
            throw new IllegalArgumentException("User Reference must contain either account Id or username");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserSummary findUserByAccountId(String accountId) {
        User user = userRepository.findByAccountId(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with accountId: " + accountId));
        return userMapper.toUserSummary(user);
    }

    @Override
    public User findUserEntityByAccountId(String accountId) {
        return userRepository.findByAccountId(accountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with accountId: " + accountId)
                );
    }

    @Override
    @Transactional(readOnly = true)
    public UserSummary getUserSummaryByAccountId(String accountId) {
        User user = findUserEntityByAccountId(accountId);
        return userMapper.toUserSummary(user);
    }


    @Override
    @Transactional
    public User saveAndFlushUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User findUserByIdWithProjects(Long userId) {
        return userRepository.findByIdWithProjects(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    /** Lookup by Jira accountId */
    @Override
    public Optional<User> findById(String accountId) {
        return userRepository.findByAccountId(accountId);
    }

    /** Lookup by username (e.g. “john.doe”) */
    @Override
    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }


}
