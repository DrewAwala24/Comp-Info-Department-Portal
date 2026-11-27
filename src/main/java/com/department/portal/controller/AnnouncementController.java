package com.department.portal.controller;

import com.department.portal.model.Announcement;
import com.department.portal.model.Course;
import com.department.portal.model.User;
import com.department.portal.service.AnnouncementService;
import com.department.portal.service.CourseService;
import com.department.portal.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final CourseService courseService;
    private final UserService userService;

    public AnnouncementController(AnnouncementService announcementService,
                                   CourseService courseService,
                                   UserService userService) {
        this.announcementService = announcementService;
        this.courseService = courseService;
        this.userService = userService;
    }

    /** Render the full announcements bulletin page */
    @GetMapping("/announcements")
    public String showAnnouncementsPage(Model model) {
        model.addAttribute("announcements", announcementService.getAllAnnouncements());
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("newAnnouncement", new Announcement());
        return "announcements";
    }

    /** Handle web form submission from the bulletin or dashboard */
    @PostMapping("/announcements")
    @PreAuthorize("hasAnyRole('ADMIN','LECTURER')")
    public String createAnnouncementFromForm(@ModelAttribute Announcement announcement,
                                              @RequestParam(value = "targetCourseId", required = false) Long targetCourseId,
                                              Principal principal) {
        // Resolve target course if one was chosen
        if (targetCourseId != null) {
            courseService.getCourseById(targetCourseId).ifPresent(announcement::setTargetCourse);
        }
        // Stamp the author
        if (principal != null) {
            userService.getUserByUsername(principal.getName()).ifPresent(announcement::setAuthor);
        }
        announcementService.createAnnouncement(announcement);
        return "redirect:/announcements?created";
    }

    /** JSON / REST endpoint for mobile or external clients */
    @PostMapping("/api/announcements")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN','LECTURER')")
    public ResponseEntity<Announcement> createAnnouncementFromApi(@RequestBody Announcement announcement,
                                                                   Principal principal) {
        if (principal != null) {
            userService.getUserByUsername(principal.getName()).ifPresent(announcement::setAuthor);
        }
        return ResponseEntity.ok(announcementService.createAnnouncement(announcement));
    }

    /** Delete an announcement (admin only) */
    @PostMapping("/announcements/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id);
        return "redirect:/announcements";
    }
}
