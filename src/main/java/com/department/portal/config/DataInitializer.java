package com.department.portal.config;

import com.department.portal.model.Course;
import com.department.portal.model.Role;
import com.department.portal.model.User;
import com.department.portal.repository.CourseRepository;
import com.department.portal.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository,
                                      CourseRepository courseRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            // 1. Seed Initial Users
            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setFullName("System Administrator"); // Added
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setEmail("admin@dept.ac.ke");
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);

                User lecturer = new User();
                lecturer.setUsername("Mr.Reuel");
                lecturer.setFullName("Reuel Lecturer"); // Added
                lecturer.setPassword(passwordEncoder.encode("lecturer123"));
                lecturer.setEmail("Reuel@dept.ac.ke");
                lecturer.setRole(Role.LECTURER);
                userRepository.save(lecturer);

                User student = new User();
                student.setUsername("Andrew Awala");
                student.setFullName("Andrew Awala"); // Added
                student.setPassword(passwordEncoder.encode("student123"));
                student.setEmail("Andrew123@student.dept.ac.ke");
                student.setRole(Role.STUDENT);
                userRepository.save(student);

                System.out.println(">>> Database Seeded: Admin, Lecturer, and Student accounts created.");
            }

            // 2. Seed Sample Courses
            if (courseRepository.count() == 0) {
                Course course1 = new Course(
                        "DCS241",
                        "Database Management Systems",
                        "Relational database design, SQL querying, and transaction management.",
                        2,
                        "Semester 1"
                );

                Course course2 = new Course(
                        "DICT241",
                        "Network Architecture & Protocols",
                        "OSI & TCP/IP models, routing protocols, and subnetting.",
                        2,
                        "Semester 2"
                );

                courseRepository.save(course1);
                courseRepository.save(course2);

                System.out.println(">>> Database Seeded: Sample courses added.");
            }
        };
    }
}