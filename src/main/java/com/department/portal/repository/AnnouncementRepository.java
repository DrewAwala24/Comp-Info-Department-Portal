package com.department.portal.repository;

import com.department.portal.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    /** All announcements ordered newest-first */
    List<Announcement> findAllByOrderByCreatedAtDesc();

    /** Course-specific announcements */
    List<Announcement> findByTargetCourse_Id(Long courseId);

    /** Student feed: course-targeted OR global (targetCourse IS NULL), newest-first */
    List<Announcement> findByTargetCourse_IdOrTargetCourseIsNullOrderByCreatedAtDesc(Long courseId);
}
