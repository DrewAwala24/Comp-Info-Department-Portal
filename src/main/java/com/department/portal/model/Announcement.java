package com.department.portal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "announcements")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Title is required")
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "Content is required")
    private String content;

    /** Null = global broadcast to all courses */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course targetCourse;

    /** The staff member who published the announcement */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    /** True when the announcement is a global broadcast (not targeted at one course). */
    public boolean isGlobal() {
        return this.targetCourse == null;
    }

    @Override
    public String toString() {
        return "Announcement{id=" + id + ", title='" + title + "', isGlobal=" + isGlobal() + '}';
    }
}
