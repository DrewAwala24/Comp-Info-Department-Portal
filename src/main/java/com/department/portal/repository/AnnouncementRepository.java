package com.department.portal.repository;

import com.department.portal.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    // Matches 'targetCourse' property and drills into its 'id'
    List<Announcement> findByTargetCourse_Id(Long courseId);

    // Matches 'targetCourse' ID OR global broadcasts where targetCourse is null
    List<Announcement> findByTargetCourse_IdOrTargetCourseIsNull(Long courseId);
}