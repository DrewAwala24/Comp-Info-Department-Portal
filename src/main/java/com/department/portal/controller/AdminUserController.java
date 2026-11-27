package com.department.portal.controller;

import com.department.portal.model.Role;
import com.department.portal.service.CourseService;
import com.department.portal.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final UserService userService;
    private final CourseService courseService;

    public AdminUserController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping
    public String listUsers(@RequestParam(value = "q", required = false) String query,
                            Model model) {
        model.addAttribute("users", userService.searchUsers(query));
        model.addAttribute("query", query);
        model.addAttribute("roles", Role.values());
        model.addAttribute("courses", courseService.getAllCourses());
        return "admin/users";
    }

    @PostMapping("/{id}/role")
    public String updateRole(@PathVariable Long id,
                             @RequestParam("role") Role role,
                             RedirectAttributes ra) {
        userService.updateRole(id, role);
        ra.addFlashAttribute("successMsg", "User role updated to " + role.name() + ".");
        return "redirect:/admin/users";
    }

    @PostMapping("/{id}/course")
    public String assignCohort(@PathVariable Long id,
                               @RequestParam(value = "courseId", required = false) Long courseId,
                               RedirectAttributes ra) {
        userService.assignCourse(id, courseId);
        ra.addFlashAttribute("successMsg", "Student intake cohort updated.");
        return "redirect:/admin/users";
    }

    @PostMapping("/{id}/toggle")
    public String toggleAccount(@PathVariable Long id, RedirectAttributes ra) {
        userService.toggleEnabled(id);
        ra.addFlashAttribute("successMsg", "Account status toggled.");
        return "redirect:/admin/users";
    }
}
