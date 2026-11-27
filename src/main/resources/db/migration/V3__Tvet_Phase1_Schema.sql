-- ============================================================
-- V3__Tvet_Phase1_Schema.sql
-- Kitale National Polytechnic – Computing & Informatics Dept
-- Phase 1: TVET Program Levels, Intake Cohorts, and Unit Load
-- ============================================================

-- ─── COURSES: Add TVET Level & Intake Cohort ─────────────
ALTER TABLE courses
    ADD COLUMN IF NOT EXISTS tvet_level    VARCHAR(30) NOT NULL DEFAULT 'LEVEL_6_DIPLOMA',
    ADD COLUMN IF NOT EXISTS intake_cohort VARCHAR(40);

ALTER TABLE courses
    DROP CONSTRAINT IF EXISTS chk_courses_tvet_level;

ALTER TABLE courses
    ADD CONSTRAINT chk_courses_tvet_level
        CHECK (tvet_level IN ('LEVEL_6_DIPLOMA', 'LEVEL_5_CRAFT', 'LEVEL_4_ARTISAN'));

CREATE INDEX IF NOT EXISTS idx_courses_tvet_level ON courses(tvet_level);

-- Populate intake cohorts for existing seeded courses
UPDATE courses SET tvet_level = 'LEVEL_6_DIPLOMA', intake_cohort = 'DCS-24S'  WHERE course_code = 'DCS241'  AND intake_cohort IS NULL;
UPDATE courses SET tvet_level = 'LEVEL_6_DIPLOMA', intake_cohort = 'DICT-24S' WHERE course_code = 'DICT241' AND intake_cohort IS NULL;
UPDATE courses SET tvet_level = 'LEVEL_6_DIPLOMA', intake_cohort = 'DCS-23S'  WHERE course_code = 'DCS301'  AND intake_cohort IS NULL;
UPDATE courses SET tvet_level = 'LEVEL_6_DIPLOMA', intake_cohort = 'DCS-23S'  WHERE course_code = 'DCS302'  AND intake_cohort IS NULL;
UPDATE courses SET tvet_level = 'LEVEL_6_DIPLOMA', intake_cohort = 'DICT-22S' WHERE course_code = 'DICT401' AND intake_cohort IS NULL;

-- ─── UNITS: Add Lecturer Assignment & Contact Hours ──────
ALTER TABLE units
    ADD COLUMN IF NOT EXISTS lecturer_id     BIGINT  REFERENCES users(id) ON DELETE SET NULL,
    ADD COLUMN IF NOT EXISTS theory_hours    INTEGER NOT NULL DEFAULT 30 CHECK (theory_hours >= 0),
    ADD COLUMN IF NOT EXISTS practical_hours INTEGER NOT NULL DEFAULT 30 CHECK (practical_hours >= 0);

CREATE INDEX IF NOT EXISTS idx_units_lecturer_id ON units(lecturer_id);
