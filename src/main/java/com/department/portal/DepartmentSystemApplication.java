package com.department.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Entry point for the Comp & Information Science Department Portal.
 * <p>
 * {@code @EnableAsync} allows the {@link com.department.portal.service.EmailService}
 * to dispatch notification emails on a separate thread without blocking web requests.
 */
@SpringBootApplication
@EnableAsync
public class DepartmentSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(DepartmentSystemApplication.class, args);
    }
}
