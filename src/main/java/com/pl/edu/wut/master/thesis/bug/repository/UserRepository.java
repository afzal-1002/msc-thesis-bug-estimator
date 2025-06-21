package com.pl.edu.wut.master.thesis.bug.repository;

import com.pl.edu.wut.master.thesis.bug.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
