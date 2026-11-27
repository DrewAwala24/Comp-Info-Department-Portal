package com.department.portal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "timetables")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Timetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    @NotNull(message = "Course is required")
    private Course course;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lecturer_id", nullable = false)
    @NotNull(message = "Lecturer is required")
    private User lecturer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false, length = 15)
    @NotNull(message = "Day of week is required")
    private DayOfWeek dayOfWeek;

    @Column(name = "start_time", nullable = false)
    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @Column(name = "venue_hall", nullable = false, length = 80)
    @NotBlank(message = "Venue/hall is required")
    private String venueHall;
}
