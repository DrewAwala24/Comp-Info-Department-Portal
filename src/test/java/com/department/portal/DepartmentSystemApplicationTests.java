package com.department.portal;

import com.department.portal.model.Role;
import com.department.portal.model.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Lightweight unit tests — no Spring context, no database connection needed.
 * Full integration tests (with @SpringBootTest) should be placed in a separate
 * profile and run only when a test DB is available.
 */
class DepartmentSystemApplicationTests {

    @Test
    void userBuilder_setsDefaultRoleToStudent() {
        User user = User.builder()
                .username("testuser")
                .fullName("Test User")
                .email("test@dept.ac.ke")
                .password("secret")
                .build();

        assertThat(user.getRole()).isEqualTo(Role.STUDENT);
        assertThat(user.isEnabled()).isTrue();
    }

    @Test
    void userBuilder_adminRoleIsHonoured() {
        User admin = User.builder()
                .username("admin")
                .fullName("Admin User")
                .email("admin@dept.ac.ke")
                .password("secret")
                .role(Role.ADMIN)
                .build();

        assertThat(admin.getRole()).isEqualTo(Role.ADMIN);
    }
}
