package com.department.portal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Sends a plain-text notification email asynchronously so it never blocks an HTTP request thread.
     */
    @Async
    public void sendNotificationEmail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@dept.ac.ke");
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            log.info("Email sent to {}", toEmail);
        } catch (Exception ex) {
            // Non-fatal: log but do not propagate — email failure should not break the main flow
            log.warn("Failed to send email to {}: {}", toEmail, ex.getMessage());
        }
    }
}
