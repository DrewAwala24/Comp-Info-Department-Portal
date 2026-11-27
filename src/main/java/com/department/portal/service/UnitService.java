package com.department.portal.service;

import com.department.portal.model.Unit;
import com.department.portal.repository.UnitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UnitService {

    private final UnitRepository unitRepository;

    public UnitService(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public List<Unit> getAllUnits() {
        return unitRepository.findAllByOrderByUnitCodeAsc();
    }

    public List<Unit> getUnitsByCourse(Long courseId) {
        return unitRepository.findByCourseId(courseId);
    }

    public List<Unit> getUnitsByLecturer(Long lecturerId) {
        return unitRepository.findByLecturerId(lecturerId);
    }

    public Optional<Unit> getUnitById(Long id) {
        return unitRepository.findById(id);
    }

    @Transactional
    public Unit saveUnit(Unit unit) {
        if (unit.getId() == null && unitRepository.existsByUnitCode(unit.getUnitCode())) {
            throw new IllegalArgumentException("Unit code '" + unit.getUnitCode() + "' already exists");
        }
        if (unit.getTheoryHours() == null) {
            unit.setTheoryHours(30);
        }
        if (unit.getPracticalHours() == null) {
            unit.setPracticalHours(30);
        }
        return unitRepository.save(unit);
    }

    @Transactional
    public Unit updateUnit(Long id, Unit updated) {
        Unit existing = unitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unit not found with id: " + id));
        existing.setUnitCode(updated.getUnitCode());
        existing.setUnitName(updated.getUnitName());
        existing.setCourse(updated.getCourse());
        existing.setLecturer(updated.getLecturer());
        existing.setTheoryHours(updated.getTheoryHours() != null ? updated.getTheoryHours() : 30);
        existing.setPracticalHours(updated.getPracticalHours() != null ? updated.getPracticalHours() : 30);
        return unitRepository.save(existing);
    }

    @Transactional
    public void deleteUnit(Long id) {
        unitRepository.deleteById(id);
    }
}
