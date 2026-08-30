package com.department.portal.repository;

import com.department.portal.model.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {
    List<Timetable> findByLecturerId(Long lecturerId);
    List<Timetable> findByCourseId(Long courseId);
    List<Timetable> findByDayOfWeek(DayOfWeek dayOfWeek);
}