package com.department.portal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "courses")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_code", nullable = false, unique = true, length = 20)
    @NotBlank(message = "Course code is required")
    private String courseCode;

    @Column(name = "course_name", nullable = false, length = 150)
    @NotBlank(message = "Course name is required")
    private String courseName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "year_of_study")
    @Min(value = 1, message = "Year of study must be at least 1")
    @Max(value = 6, message = "Year of study must be at most 6")
    private Integer yearOfStudy;

    @Column(length = 20)
    private String semester;

    @Enumerated(EnumType.STRING)
    @Column(name = "tvet_level", nullable = false, length = 30)
    @NotNull(message = "TVET level is required")
    @Builder.Default
    private TvetLevel tvetLevel = TvetLevel.LEVEL_6_DIPLOMA;

    /** Intake cohort code, e.g., DCS-24S, ICT-25J */
    @Column(name = "intake_cohort", length = 40)
    private String intakeCohort;

    @Override
    public String toString() {
        return courseCode + " – " + courseName;
    }
}
