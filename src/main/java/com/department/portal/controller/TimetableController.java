package com.department.portal.controller;

import com.department.portal.model.Timetable;
import com.department.portal.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequestMapping("/api/timetables")
public class TimetableController {

    private final TimetableService timetableService;

    @Autowired
    public TimetableController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @PostMapping
    public ResponseEntity<Timetable> createTimetable(@RequestBody Timetable timetable) {
        Timetable savedTimetable = timetableService.saveTimetable(timetable);
        return new ResponseEntity<>(savedTimetable, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Timetable>> getAllTimetables() {
        return ResponseEntity.ok(timetableService.getAllTimetables());
    }

    @GetMapping("/lecturer/{lecturerId}")
    public ResponseEntity<List<Timetable>> getTimetableByLecturer(@PathVariable Long lecturerId) {
        return ResponseEntity.ok(timetableService.getTimetableByLecturer(lecturerId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Timetable>> getTimetableByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(timetableService.getTimetableByCourse(courseId));
    }

    @GetMapping("/day/{day}")
    public ResponseEntity<List<Timetable>> getTimetableByDay(@PathVariable DayOfWeek day) {
        return ResponseEntity.ok(timetableService.getTimetableByDay(day));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimetable(@PathVariable Long id) {
        timetableService.deleteTimetable(id);
        return ResponseEntity.noContent().build();
    }
}