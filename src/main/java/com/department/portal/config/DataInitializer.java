package com.department.portal.config;

import com.department.portal.model.Course;
import com.department.portal.model.Role;
import com.department.portal.model.User;
import com.department.portal.repository.CourseRepository;
import com.department.portal.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Seeds baseline accounts and sample courses on first startup when the
 * tables are empty. Flyway handles the schema; this runner handles runtime data
 * that depends on a live PasswordEncoder bean.
 */
@Configuration
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    public CommandLineRunner initData(UserRepository userRepository,
                                     CourseRepository courseRepository,
                                     PasswordEncoder passwordEncoder) {
        return args -> {

            // ── Seed users (only if none exist yet) ───────────────────────
            if (userRepository.count() == 0) {

                User admin = User.builder()
                        .username("admin")
                        .fullName("System Administrator")
                        .password(passwordEncoder.encode("admin123"))
                        .email("admin@dept.ac.ke")
                        .role(Role.ADMIN)
                        .enabled(true)
                        .build();
                userRepository.save(admin);

                User lecturer = User.builder()
                        .username("mr.reuel")
                        .fullName("Reuel Mwangi")
                        .password(passwordEncoder.encode("lecturer123"))
                        .email("reuel@dept.ac.ke")
                        .role(Role.LECTURER)
                        .enabled(true)
                        .build();
                userRepository.save(lecturer);

                User student = User.builder()
                        .username("andrew.awala")
                        .fullName("Andrew Awala")
                        .password(passwordEncoder.encode("student123"))
                        .email("andrew@student.dept.ac.ke")
                        .role(Role.STUDENT)
                        .enabled(true)
                        .build();
                userRepository.save(student);

                log.info(">>> Seeded: admin, mr.reuel, andrew.awala accounts.");
            } else {
                // Repair placeholder hash from V2__Seed_Data.sql if still present
                userRepository.findByUsername("admin").ifPresent(admin -> {
                    if ("$2a$12$uIdPJPNcD1g4EeMLGMhfSuF1R.o3l4B0p.i6IZwFm9Y5KLiVRzP4K".equals(admin.getPassword())) {
                        admin.setPassword(passwordEncoder.encode("admin123"));
                        userRepository.save(admin);
                        log.info(">>> Repaired seeded admin password hash.");
                    }
                });
            }

            // ── Seed sample courses (only if none exist yet) ───────────────
            if (courseRepository.count() == 0) {
                courseRepository.save(Course.builder()
                        .courseCode("DCS241")
                        .courseName("Database Management Systems")
                        .description("Relational database design, SQL, normalisation, and transactions.")
                        .yearOfStudy(2).semester("Semester 1").build());

                courseRepository.save(Course.builder()
                        .courseCode("DICT241")
                        .courseName("Network Architecture & Protocols")
                        .description("OSI & TCP/IP models, routing protocols, and subnetting.")
                        .yearOfStudy(2).semester("Semester 2").build());

                courseRepository.save(Course.builder()
                        .courseCode("DCS301")
                        .courseName("Software Engineering")
                        .description("SDLC, Agile, UML, and software project management.")
                        .yearOfStudy(3).semester("Semester 1").build());

                log.info(">>> Seeded: 3 sample courses.");
            }
        };
    }
}
