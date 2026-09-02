package com.department.portal.service;

import com.department.portal.model.Announcement;
import com.department.portal.model.User;
import com.department.portal.repository.AnnouncementRepository;
import com.department.portal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public AnnouncementService(AnnouncementRepository announcementRepository,
                               UserRepository userRepository,
                               EmailService emailService) {
        this.announcementRepository = announcementRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }

    public List<Announcement> getAnnouncementsForCourse(Long courseId) {
        // Fetches announcements targeting the student's course OR global broadcasts
        return announcementRepository.findByTargetCourse_IdOrTargetCourseIsNull(courseId);
    }

    public Announcement createAnnouncement(Announcement announcement) {
        if (announcement.getCreatedAt() == null) {
            announcement.setCreatedAt(LocalDateTime.now());
        }

        Announcement saved = announcementRepository.save(announcement);

        // Fetch recipients based on targeted course or global dispatch
        List<User> recipients;
        if (saved.getTargetCourse() != null) {
            recipients = userRepository.findByCourseId(saved.getTargetCourse().getId());
        } else {
            recipients = userRepository.findAll();
        }

        // Trigger email dispatch
        for (User user : recipients) {
            if (user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
                emailService.sendNotificationEmail(
                        user.getEmail(),
                        "New Department Announcement: " + saved.getTitle(),
                        saved.getContent()
                );
            }
        }

        return saved;
    }
}