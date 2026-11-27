package com.department.portal.service;

import com.department.portal.model.Announcement;
import com.department.portal.model.User;
import com.department.portal.repository.AnnouncementRepository;
import com.department.portal.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AnnouncementService {

    private static final Logger log = LoggerFactory.getLogger(AnnouncementService.class);

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
        return announcementRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Announcement> getAnnouncementsForCourse(Long courseId) {
        return announcementRepository.findByTargetCourse_IdOrTargetCourseIsNullOrderByCreatedAtDesc(courseId);
    }

    @Transactional
    public Announcement createAnnouncement(Announcement announcement) {
        if (announcement.getCreatedAt() == null) {
            announcement.setCreatedAt(LocalDateTime.now());
        }
        Announcement saved = announcementRepository.save(announcement);

        // Determine recipients
        List<User> recipients = (saved.getTargetCourse() != null)
                ? userRepository.findByCourseId(saved.getTargetCourse().getId())
                : userRepository.findAll();

        String emailSubject = "📢 Department Announcement: " + saved.getTitle();

        // Fire-and-forget email dispatch (async)
        for (User user : recipients) {
            if (user.getEmail() != null && !user.getEmail().isBlank()) {
                emailService.sendNotificationEmail(user.getEmail(), emailSubject, saved.getContent());
            }
        }

        log.info("Announcement '{}' created; {} notification(s) dispatched.", saved.getTitle(), recipients.size());
        return saved;
    }

    @Transactional
    public void deleteAnnouncement(Long id) {
        announcementRepository.deleteById(id);
    }
}
