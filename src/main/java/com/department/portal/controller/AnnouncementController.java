package com.department.portal.controller;

import com.department.portal.model.Announcement;
import com.department.portal.service.AnnouncementService;
import com.department.portal.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final CourseService courseService;

    public AnnouncementController(AnnouncementService announcementService, CourseService courseService) {
        this.announcementService = announcementService;
        this.courseService = courseService;
    }

    // Render announcements page/dashboard view
    @GetMapping("/announcements")
    public String showAnnouncementsPage(Model model) {
        List<Announcement> announcements = announcementService.getAllAnnouncements();
        model.addAttribute("announcements", announcements);
        model.addAttribute("courses", courseService.getAllCourses()); // Fixed: Populates dropdown options
        model.addAttribute("newAnnouncement", new Announcement());
        return "announcements";
    }

    // Handles web form submissions for new announcements
    @PostMapping("/announcements")
    public String createAnnouncementFromForm(@ModelAttribute Announcement announcement) {
        announcementService.createAnnouncement(announcement);
        return "redirect:/announcements?created";
    }

    // Handles JSON API requests for mobile or fetch clients
    @PostMapping("/api/announcements")
    @ResponseBody
    public ResponseEntity<Announcement> createAnnouncementFromApi(@RequestBody Announcement announcement) {
        Announcement saved = announcementService.createAnnouncement(announcement);
        return ResponseEntity.ok(saved);
    }
}