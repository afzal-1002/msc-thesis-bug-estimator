package com.pl.edu.wut.master.thesis.bug.repository;

import com.pl.edu.wut.master.thesis.bug.model.user.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAddress(String email);

    Optional<User> findByUsernameAndPassword(String userName, String password, Sort sort);
    Optional<User> findByAccountId(String accountId);
    Optional<User> findByAccountIdAndPassword(String accountId, String password);
    Optional<User> findByEmailAddressAndPassword(String email, String password);

    Optional<User> findByEmailAddressOrUsername(String emailAddress, String userName);

    @EntityGraph(attributePaths = "projects")
    Optional<User> findByUsername(String username);

    Optional<User>  findUserEntityById(Long userId);
    Optional<User> findUserByAccountId(String accountId);
    Optional<User>  findUserEntityByAccountId(String accountId);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.projects WHERE u.id = :userId")
    Optional<User> findByIdWithProjects(@Param("userId") Long userId);

    boolean existsByUsername(String username);


//    @Query("SELECT COUNT(up) > 0 FROM UserProject up WHERE up.user.id = :userId AND up.project.id = :projectId")
//    boolean existsByUserIdAndProjectId(@Param("userId") Long userId, @Param("projectId") Long projectId);

}
