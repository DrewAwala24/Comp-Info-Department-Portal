package com.department.portal.service;

import com.department.portal.model.Course;
import com.department.portal.model.TvetLevel;
import com.department.portal.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional
    public Course saveCourse(Course course) {
        if (course.getId() == null && courseRepository.existsByCourseCode(course.getCourseCode())) {
            throw new IllegalArgumentException("Course code '" + course.getCourseCode() + "' already exists");
        }
        if (course.getTvetLevel() == null) {
            course.setTvetLevel(TvetLevel.LEVEL_6_DIPLOMA);
        }
        return courseRepository.save(course);
    }

    @Transactional
    public Course updateCourse(Long id, Course updated) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + id));
        existing.setCourseCode(updated.getCourseCode());
        existing.setCourseName(updated.getCourseName());
        existing.setDescription(updated.getDescription());
        existing.setYearOfStudy(updated.getYearOfStudy());
        existing.setSemester(updated.getSemester());
        existing.setTvetLevel(updated.getTvetLevel() != null ? updated.getTvetLevel() : TvetLevel.LEVEL_6_DIPLOMA);
        existing.setIntakeCohort(updated.getIntakeCohort());
        return courseRepository.save(existing);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAllByOrderByCourseCodeAsc();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Optional<Course> getCourseByCode(String courseCode) {
        return courseRepository.findByCourseCode(courseCode);
    }

    public List<Course> getCoursesByYear(Integer yearOfStudy) {
        return courseRepository.findByYearOfStudy(yearOfStudy);
    }

    public List<Course> getCoursesByTvetLevel(TvetLevel level) {
        return courseRepository.findByTvetLevel(level);
    }

    @Transactional
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
