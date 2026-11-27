package com.department.portal.repository;

import com.department.portal.model.Course;
import com.department.portal.model.TvetLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseCode(String courseCode);
    boolean existsByCourseCode(String courseCode);
    List<Course> findByYearOfStudy(Integer yearOfStudy);
    List<Course> findBySemester(String semester);
    List<Course> findByTvetLevel(TvetLevel tvetLevel);
    List<Course> findByIntakeCohort(String intakeCohort);
    List<Course> findAllByOrderByCourseCodeAsc();
}
