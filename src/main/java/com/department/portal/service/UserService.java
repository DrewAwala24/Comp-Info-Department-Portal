package com.department.portal.service;

import com.department.portal.model.Course;
import com.department.portal.model.Role;
import com.department.portal.model.User;
import com.department.portal.repository.CourseRepository;
import com.department.portal.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       CourseRepository courseRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username '" + user.getUsername() + "' is already taken");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email '" + user.getEmail() + "' is already registered");
        }
        if (user.getRole() == null) {
            user.setRole(Role.STUDENT);
        }
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAllByOrderByFullNameAsc();
    }

    public List<User> searchUsers(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return userRepository.findAllByOrderByFullNameAsc();
        }
        return userRepository.searchByKeyword(keyword.trim());
    }

    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void updateRole(Long userId, Role role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setRole(role);
        userRepository.save(user);
    }

    @Transactional
    public void assignCourse(Long userId, Long courseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (courseId == null) {
            user.setCourse(null);
        } else {
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new IllegalArgumentException("Course not found"));
            user.setCourse(course);
        }
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void toggleEnabled(Long id) {
        userRepository.findById(id).ifPresent(u -> {
            u.setEnabled(!u.isEnabled());
            userRepository.save(u);
        });
    }
}
