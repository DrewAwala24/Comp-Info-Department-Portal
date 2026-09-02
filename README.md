# University Department Portal

A robust web application built with Java Spring Boot, Thymeleaf, and PostgreSQL designed to streamline academic administration, student course management, timetable scheduling, and department-wide notifications.

## Key Features Built So Far
- **Data Persistence & Schema:** Relational database integration using Spring Data JPA and PostgreSQL.
- **Domain Models & Relationships:** Entities established for `User` (Role-Based Access), `Course`, `Timetable`, and `Announcement` with `@ManyToOne` relationships linking students directly to their assigned courses.
- **Authentication & Security:** Form-based authentication configured via Spring Security with BCrypt password hashing, public access for login and self-registration endpoints, and role-based authorization restricting announcement publishing to `ADMIN` and `LECTURER` roles.
- **Automated Announcement & Email Notification System:** Full bulletin dispatch engine allowing faculty to post global or course-specific announcements that automatically generate and dispatch asynchronous emails to targeted student recipients via SMTP.
- **Student Registration & Class Assignment:** Dynamic sign-up flow allowing students to register with their admission number and select their specific class program to automatically scope dashboard data.
- **Repository Architecture & Querying:** Derived query methods to filter timetables and announcements by `course_id`, `lecturer_id`, and `createdAt` order.
- **Service Layer & View Controllers:** Business logic handling data validation, user registration with default role assignment (`Role.STUDENT`), timetable filtering, announcement routing, and dynamic Thymeleaf template rendering.
- **User Interface:** Responsive frontend styled with Bootstrap 5 and Google Fonts (Montserrat), featuring a polished maroon aesthetic across the dashboard and bulletin views.

## Tech Stack
- **Backend:** Java, Spring Boot, Spring Security, Spring Data JPA, JavaMailSender
- **Frontend / Templating:** Thymeleaf, HTML5, CSS3, Bootstrap 5
- **Database:** PostgreSQL
- **Email Gateway:** Mailtrap (Development/Testing SMTP)
- **Build Tool:** Maven
