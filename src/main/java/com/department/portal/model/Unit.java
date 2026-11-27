package com.department.portal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "units")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unit_code", nullable = false, unique = true, length = 20)
    @NotBlank(message = "Unit code is required")
    private String unitCode;

    @Column(name = "unit_name", nullable = false, length = 150)
    @NotBlank(message = "Unit name is required")
    private String unitName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    @NotNull(message = "Course is required")
    private Course course;

    /** Assigned lecturer (ROLE_LECTURER) */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lecturer_id")
    private User lecturer;

    @Column(name = "theory_hours", nullable = false)
    @Min(value = 0, message = "Theory hours cannot be negative")
    @Builder.Default
    private Integer theoryHours = 30;

    @Column(name = "practical_hours", nullable = false)
    @Min(value = 0, message = "Practical hours cannot be negative")
    @Builder.Default
    private Integer practicalHours = 30;

    public int getTotalHours() {
        int t = theoryHours != null ? theoryHours : 0;
        int p = practicalHours != null ? practicalHours : 0;
        return t + p;
    }

    @Override
    public String toString() {
        return unitCode + " – " + unitName;
    }
}
