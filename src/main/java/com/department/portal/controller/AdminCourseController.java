package com.department.portal.controller;

import com.department.portal.model.Course;
import com.department.portal.model.Role;
import com.department.portal.model.TvetLevel;
import com.department.portal.model.Unit;
import com.department.portal.service.CourseService;
import com.department.portal.service.UnitService;
import com.department.portal.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminCourseController {

    private final CourseService courseService;
    private final UnitService unitService;
    private final UserService userService;

    public AdminCourseController(CourseService courseService,
                                 UnitService unitService,
                                 UserService userService) {
        this.courseService = courseService;
        this.unitService = unitService;
        this.userService = userService;
    }

    // ── TVET Programs / Courses ──────────────────────────────────────────────

    @GetMapping("/courses")
    public String listCourses(@RequestParam(value = "level", required = false) TvetLevel level,
                              Model model) {
        model.addAttribute("courses", level != null
                ? courseService.getCoursesByTvetLevel(level)
                : courseService.getAllCourses());
        model.addAttribute("tvetLevels", TvetLevel.values());
        model.addAttribute("selectedLevel", level);
        model.addAttribute("newCourse", new Course());
        return "admin/courses";
    }

    @PostMapping("/courses")
    public String createCourse(@ModelAttribute("newCourse") Course course,
                               RedirectAttributes ra) {
        try {
            courseService.saveCourse(course);
            ra.addFlashAttribute("successMsg", "Program " + course.getCourseCode() + " created.");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("errorMsg", e.getMessage());
        }
        return "redirect:/admin/courses";
    }

    @PostMapping("/courses/{id}/update")
    public String updateCourse(@PathVariable Long id,
                               @ModelAttribute Course course,
                               RedirectAttributes ra) {
        try {
            courseService.updateCourse(id, course);
            ra.addFlashAttribute("successMsg", "Program updated.");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("errorMsg", e.getMessage());
        }
        return "redirect:/admin/courses";
    }

    @PostMapping("/courses/{id}/delete")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes ra) {
        courseService.deleteCourse(id);
        ra.addFlashAttribute("successMsg", "Program deleted.");
        return "redirect:/admin/courses";
    }

    // ── Units / Modules ──────────────────────────────────────────────────────

    @GetMapping("/units")
    public String listUnits(@RequestParam(value = "courseId", required = false) Long courseId,
                            Model model) {
        model.addAttribute("units", courseId != null
                ? unitService.getUnitsByCourse(courseId)
                : unitService.getAllUnits());
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("lecturers", userService.getUsersByRole(Role.LECTURER));
        model.addAttribute("selectedCourseId", courseId);
        model.addAttribute("newUnit", new Unit());
        return "admin/units";
    }

    @PostMapping("/units")
    public String createUnit(@ModelAttribute("newUnit") Unit unit,
                             @RequestParam("courseId") Long courseId,
                             @RequestParam(value = "lecturerId", required = false) Long lecturerId,
                             RedirectAttributes ra) {
        try {
            courseService.getCourseById(courseId).ifPresent(unit::setCourse);
            if (lecturerId != null) {
                userService.getUserById(lecturerId).ifPresent(unit::setLecturer);
            }
            unitService.saveUnit(unit);
            ra.addFlashAttribute("successMsg", "Unit " + unit.getUnitCode() + " added.");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("errorMsg", e.getMessage());
        }
        return "redirect:/admin/units";
    }

    @PostMapping("/units/{id}/update")
    public String updateUnit(@PathVariable Long id,
                             @ModelAttribute Unit unit,
                             @RequestParam("courseId") Long courseId,
                             @RequestParam(value = "lecturerId", required = false) Long lecturerId,
                             RedirectAttributes ra) {
        try {
            courseService.getCourseById(courseId).ifPresent(unit::setCourse);
            if (lecturerId != null) {
                userService.getUserById(lecturerId).ifPresent(unit::setLecturer);
            } else {
                unit.setLecturer(null);
            }
            unitService.updateUnit(id, unit);
            ra.addFlashAttribute("successMsg", "Unit updated.");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("errorMsg", e.getMessage());
        }
        return "redirect:/admin/units";
    }

    @PostMapping("/units/{id}/delete")
    public String deleteUnit(@PathVariable Long id, RedirectAttributes ra) {
        unitService.deleteUnit(id);
        ra.addFlashAttribute("successMsg", "Unit deleted.");
        return "redirect:/admin/units";
    }
}
