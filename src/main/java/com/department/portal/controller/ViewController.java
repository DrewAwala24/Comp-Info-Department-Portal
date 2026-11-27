package com.department.portal.controller;

import com.department.portal.model.User;
import com.department.portal.service.CourseService;
import com.department.portal.service.AnnouncementService;
import com.department.portal.service.TimetableService;
import com.department.portal.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class ViewController {

    private final CourseService courseService;
    private final TimetableService timetableService;
    private final UserService userService;
    private final AnnouncementService announcementService;

    public ViewController(CourseService courseService,
                          TimetableService timetableService,
                          UserService userService,
                          AnnouncementService announcementService) {
        this.courseService = courseService;
        this.timetableService = timetableService;
        this.userService = userService;
        this.announcementService = announcementService;
    }

    /** Redirect root → dashboard */
    @GetMapping("/")
    public String root() {
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("courses", courseService.getAllCourses());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") User user) {
        try {
            userService.registerUser(user);
            return "redirect:/login?registered";
        } catch (IllegalArgumentException e) {
            return "redirect:/register?error=" + e.getMessage();
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        User currentUser = userService.getUserByUsername(username).orElse(null);

        model.addAttribute("currentUser", currentUser);

        if (currentUser != null && currentUser.getCourse() != null) {
            Long courseId = currentUser.getCourse().getId();
            model.addAttribute("userCourse", currentUser.getCourse());
            model.addAttribute("timetables", timetableService.getTimetablesByCourseId(courseId));
            model.addAttribute("announcements", announcementService.getAnnouncementsForCourse(courseId));
        } else {
            model.addAttribute("courses", courseService.getAllCourses());
            model.addAttribute("timetables", timetableService.getAllTimetables());
            model.addAttribute("announcements", announcementService.getAllAnnouncements());
        }

        // Always populate the courses dropdown for the announcement form
        if (!model.containsAttribute("courses")) {
            model.addAttribute("courses", courseService.getAllCourses());
        }

        return "Dashboard";
    }
}
