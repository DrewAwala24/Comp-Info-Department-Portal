package com.department.portal.repository;

import com.department.portal.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    // Fetches announcements for a specific course (e.g. DCS 241) or global broadcasts (null targetCourse)
    List<Announcement> findByTargetCourseIdOrTargetCourseIsNull(Long courseId);
}