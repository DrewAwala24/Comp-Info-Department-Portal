package com.department.portal.repository;

import com.department.portal.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    List<Unit> findByCourseId(Long courseId);
    List<Unit> findByLecturerId(Long lecturerId);
    List<Unit> findAllByOrderByUnitCodeAsc();
    boolean existsByUnitCode(String unitCode);
}
