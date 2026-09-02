package com.department.portal.repository;

import com.department.portal.model.Role;
import com.department.portal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    // Fetch all students enrolled in a specific class/course for automated email alerts
    List<User> findByCourseId(Long courseId);
}