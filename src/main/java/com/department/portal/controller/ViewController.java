package com.department.portal.controller;

import com.department.portal.model.Course;
import com.department.portal.model.User;
import com.department.portal.service.CourseService;
import com.department.portal.service.TimetableService;
import com.department.portal.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class ViewController {

    private final CourseService courseService;
    private final TimetableService timetableService;
    private final UserService userService;

    public ViewController(CourseService courseService, TimetableService timetableService, UserService userService) {
        this.courseService = courseService;
        this.timetableService = timetableService;
        this.userService = userService;
    }

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
        userService.registerUser(user);
        return "redirect:/login?registered";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User currentUser = userService.getUserByUsername(username).orElse(null);

            if (currentUser != null && currentUser.getCourse() != null) {
                Long courseId = currentUser.getCourse().getId();
                model.addAttribute("userCourse", currentUser.getCourse());
                model.addAttribute("timetables", timetableService.getTimetablesByCourseId(courseId));
            } else {
                model.addAttribute("courses", courseService.getAllCourses());
                model.addAttribute("timetables", timetableService.getAllTimetables());
            }
        }
        return "dashboard";
    }
}