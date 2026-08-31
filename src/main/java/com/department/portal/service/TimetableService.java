package com.department.portal.service;

import com.department.portal.model.Timetable;
import com.department.portal.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Service
public class TimetableService {

    private final TimetableRepository timetableRepository;

    @Autowired
    public TimetableService(TimetableRepository timetableRepository) {
        this.timetableRepository = timetableRepository;
    }

    public Timetable saveTimetable(Timetable timetable) {
        return timetableRepository.save(timetable);
    }

    public List<Timetable> getAllTimetables() {
        return timetableRepository.findAll();
    }

    public List<Timetable> getTimetableByLecturer(Long lecturerId) {
        return timetableRepository.findByLecturerId(lecturerId);
    }

    // Renamed to getTimetablesByCourseId to resolve ViewController compiler error
    public List<Timetable> getTimetablesByCourseId(Long courseId) {
        return timetableRepository.findByCourseId(courseId);
    }

    public List<Timetable> getTimetableByCourse(Long courseId) {
        return timetableRepository.findByCourseId(courseId);
    }

    public List<Timetable> getTimetableByDay(DayOfWeek dayOfWeek) {
        return timetableRepository.findByDayOfWeek(dayOfWeek);
    }

    public void deleteTimetable(Long id) {
        timetableRepository.deleteById(id);
    }
}