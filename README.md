# University Department Portal

A robust web application built with Java Spring Boot, Thymeleaf, and PostgreSQL designed to streamline academic administration, student course management, and timetable scheduling.

## Key Features Built So Far
- **Data Persistence & Schema:** Relational database integration using Spring Data JPA and PostgreSQL.
- **Domain Models & Relationships:** Entities established for `User` (Role-Based Access), `Course`, and `Timetable` with `@ManyToOne` relationships linking students directly to their assigned courses[cite: 4].
- **Authentication & Security:** Form-based authentication configured via Spring Security with BCrypt password hashing and public access for login and self-registration endpoints[cite: 3].
- **Student Registration & Class Assignment:** Dynamic sign-up flow allowing students to register with their admission number and select their specific class program (e.g., DCS241) to automatically scope dashboard data[cite: 4, 5, 6].
- **Repository Architecture & Querying:** Derived query methods to filter timetables and announcements by `course_id`, `lecturer_id`, and `dayOfWeek`[cite: 7].
- **Service Layer & View Controllers:** Business logic handling data validation, user registration with default role assignment (`Role.STUDENT`)[cite: 3], timetable filtering, and dynamic Thymeleaf template rendering[cite: 5].
- **User Interface:** Responsive frontend styled with Bootstrap 5 and Google Fonts (Montserrat), featuring a polished maroon aesthetic[cite: 6].

## Tech Stack
- **Backend:** Java 17+, Spring Boot, Spring Security, Spring Data JPA
- **Frontend / Templating:** Thymeleaf, HTML5, CSS3, Bootstrap 5
- **Database:** PostgreSQL (managed via JetBrains DataGrip / Hibernate)
- **Build Tool:** Maven

