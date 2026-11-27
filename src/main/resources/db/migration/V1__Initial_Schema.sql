-- ============================================================
-- V1__Initial_Schema.sql
-- Comp & Information Science Department Portal
-- Full initial database schema for comp_info_dept_portal
-- ============================================================

-- ─── COURSES ─────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS courses (
    id            BIGSERIAL    PRIMARY KEY,
    course_code   VARCHAR(20)  NOT NULL UNIQUE,
    course_name   VARCHAR(150) NOT NULL,
    description   TEXT,
    year_of_study INTEGER      CHECK (year_of_study BETWEEN 1 AND 6),
    semester      VARCHAR(20)
);

-- ─── USERS ───────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS users (
    id         BIGSERIAL    PRIMARY KEY,
    username   VARCHAR(80)  NOT NULL UNIQUE,
    email      VARCHAR(150) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    full_name  VARCHAR(150) NOT NULL,
    role       VARCHAR(20)  NOT NULL DEFAULT 'STUDENT'
                             CHECK (role IN ('ADMIN','LECTURER','STUDENT')),
    course_id  BIGINT       REFERENCES courses(id) ON DELETE SET NULL,
    is_enabled BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_users_role      ON users(role);
CREATE INDEX IF NOT EXISTS idx_users_course_id ON users(course_id);

-- ─── UNITS ───────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS units (
    id         BIGSERIAL    PRIMARY KEY,
    unit_code  VARCHAR(20)  NOT NULL UNIQUE,
    unit_name  VARCHAR(150) NOT NULL,
    course_id  BIGINT       NOT NULL REFERENCES courses(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_units_course_id ON units(course_id);

-- ─── TIMETABLES ──────────────────────────────────────────
CREATE TABLE IF NOT EXISTS timetables (
    id           BIGSERIAL   PRIMARY KEY,
    course_id    BIGINT      NOT NULL REFERENCES courses(id) ON DELETE CASCADE,
    lecturer_id  BIGINT      NOT NULL REFERENCES users(id)   ON DELETE RESTRICT,
    unit_id      BIGINT               REFERENCES units(id)   ON DELETE SET NULL,
    day_of_week  VARCHAR(15) NOT NULL
                              CHECK (day_of_week IN ('MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY','SUNDAY')),
    start_time   TIME        NOT NULL,
    end_time     TIME        NOT NULL,
    venue_hall   VARCHAR(80) NOT NULL,
    CONSTRAINT chk_timetable_times CHECK (end_time > start_time)
);

CREATE INDEX IF NOT EXISTS idx_timetables_course_id   ON timetables(course_id);
CREATE INDEX IF NOT EXISTS idx_timetables_lecturer_id ON timetables(lecturer_id);
CREATE INDEX IF NOT EXISTS idx_timetables_day         ON timetables(day_of_week);

-- ─── ANNOUNCEMENTS ───────────────────────────────────────
CREATE TABLE IF NOT EXISTS announcements (
    id           BIGSERIAL    PRIMARY KEY,
    title        VARCHAR(200) NOT NULL,
    content      TEXT         NOT NULL,
    course_id    BIGINT                REFERENCES courses(id) ON DELETE SET NULL,
    author_id    BIGINT                REFERENCES users(id)   ON DELETE SET NULL,
    created_at   TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_announcements_course_id ON announcements(course_id);
CREATE INDEX IF NOT EXISTS idx_announcements_created   ON announcements(created_at DESC);
