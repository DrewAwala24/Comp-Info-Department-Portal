package com.department.portal;

import com.department.portal.service.EmailService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DepartmentSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(DepartmentSystemApplication.class, args);
    }

    // Temporary test on application startup to verify Mailtrap integration
    @Bean
    public CommandLineRunner testEmail(EmailService emailService) {
        return args -> {
            System.out.println(">>> Testing Mailtrap connection...");
            emailService.sendNotificationEmail(
                    "testuser@portal.ac.ke",
                    "Test Subject",
                    "If you see this, Mailtrap and EmailService are working!"
            );
        };
    }
}