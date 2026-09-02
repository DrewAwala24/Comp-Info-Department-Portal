package com.department.portal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "announcements")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course targetCourse; // Null means broadcast to ALL courses

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Default No-Args Constructor (Required by JPA)
    public Announcement() {}

    // Constructor for Global Broadcasts
    public Announcement(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Constructor for Course-Targeted Announcements
    public Announcement(String title, String content, Course targetCourse) {
        this.title = title;
        this.content = content;
        this.targetCourse = targetCourse;
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Course getTargetCourse() { return targetCourse; }
    public void setTargetCourse(Course targetCourse) { this.targetCourse = targetCourse; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // Helper method for Thymeleaf UI rendering
    public boolean isGlobal() {
        return this.targetCourse == null;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createdAt=" + createdAt +
                ", isGlobal=" + isGlobal() +
                '}';
    }
}