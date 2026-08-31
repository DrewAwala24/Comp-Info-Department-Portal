package com.department.portal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "units")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unit_code", nullable = false, unique = true)
    private String unitCode; // e.g., "DCS 2401"

    @Column(name = "unit_name", nullable = false)
    private String unitName; // e.g., "Object Oriented Programming in Java"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    public Unit() {}

    public Unit(String unitCode, String unitName, Course course) {
        this.unitCode = unitCode;
        this.unitName = unitName;
        this.course = course;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUnitCode() { return unitCode; }
    public void setUnitCode(String unitCode) { this.unitCode = unitCode; }

    public String getUnitName() { return unitName; }
    public void setUnitName(String unitName) { this.unitName = unitName; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
}