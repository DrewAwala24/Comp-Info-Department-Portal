package com.department.portal.controller;

import com.department.portal.service.CourseService;
import com.department.portal.service.TimetableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private final CourseService courseService;
    private final TimetableService timetableService;

    public ViewController(CourseService courseService, TimetableService timetableService) {
        this.courseService = courseService;
        this.timetableService = timetableService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("timetables", timetableService.getAllTimetables());
        return "dashboard";
    }
}