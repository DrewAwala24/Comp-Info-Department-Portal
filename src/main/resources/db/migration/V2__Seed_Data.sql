-- ============================================================
-- V2__Seed_Data.sql
-- Initial seed data: admin user + sample courses + units
-- ============================================================

-- ─── COURSES ─────────────────────────────────────────────
INSERT INTO courses (course_code, course_name, description, year_of_study, semester)
VALUES
  ('DCS241',  'Database Management Systems',
               'Relational database design, SQL querying, normalisation, and transaction management.',
               2, 'Semester 1'),
  ('DICT241', 'Network Architecture & Protocols',
               'OSI & TCP/IP models, routing protocols, subnetting, and network security fundamentals.',
               2, 'Semester 2'),
  ('DCS301',  'Software Engineering',
               'Software development lifecycle, Agile methodologies, UML, and project management.',
               3, 'Semester 1'),
  ('DCS302',  'Operating Systems',
               'Process management, memory management, file systems, and concurrency.',
               3, 'Semester 2'),
  ('DICT401', 'Cloud Computing & DevOps',
               'Cloud platforms (AWS/GCP), containerisation (Docker/K8s), and CI/CD pipelines.',
               4, 'Semester 1')
ON CONFLICT (course_code) DO NOTHING;

-- ─── ADMIN USER (password: admin123) ─────────────────────
-- BCrypt hash for "admin123"
INSERT INTO users (username, email, password, full_name, role, is_enabled)
VALUES (
  'admin',
  'admin@dept.ac.ke',
  '$2a$12$uIdPJPNcD1g4EeMLGMhfSuF1R.o3l4B0p.i6IZwFm9Y5KLiVRzP4K',
  'System Administrator',
  'ADMIN',
  TRUE
) ON CONFLICT (username) DO NOTHING;

-- ─── UNITS FOR DCS241 ────────────────────────────────────
INSERT INTO units (unit_code, unit_name, course_id)
SELECT u.unit_code, u.unit_name, c.id
FROM (VALUES
  ('DCS 2401', 'Introduction to Database Systems'),
  ('DCS 2402', 'SQL & Relational Algebra'),
  ('DCS 2403', 'Database Design & Normalisation'),
  ('DCS 2404', 'Transaction Management & Concurrency')
) AS u(unit_code, unit_name)
JOIN courses c ON c.course_code = 'DCS241'
ON CONFLICT (unit_code) DO NOTHING;

-- ─── UNITS FOR DICT241 ───────────────────────────────────
INSERT INTO units (unit_code, unit_name, course_id)
SELECT u.unit_code, u.unit_name, c.id
FROM (VALUES
  ('DICT 2401', 'OSI & TCP/IP Reference Models'),
  ('DICT 2402', 'IP Addressing & Subnetting'),
  ('DICT 2403', 'Routing Protocols'),
  ('DICT 2404', 'Network Security Fundamentals')
) AS u(unit_code, unit_name)
JOIN courses c ON c.course_code = 'DICT241'
ON CONFLICT (unit_code) DO NOTHING;
