package com.department.portal.config;

import com.department.portal.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // strength 12 — good balance between security and startup-time cost
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // CSRF is disabled here; re-enable & add tokens for a production system
            .csrf(csrf -> csrf.disable())
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(auth -> auth

                // Public routes
                .requestMatchers("/login", "/register", "/api/users/register",
                                 "/css/**", "/js/**", "/images/**", "/vendor/**", "/fonts/**", "/favicon.ico").permitAll()

                // Admin-only HOD management panels & APIs
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,   "/api/courses/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/courses/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/announcements/**").hasAnyRole("ADMIN")

                // Read access
                .requestMatchers(HttpMethod.GET, "/api/courses/**").hasAnyRole("ADMIN", "LECTURER", "STUDENT")

                // Timetable management
                .requestMatchers(HttpMethod.POST,   "/api/timetables/**").hasAnyRole("ADMIN", "LECTURER")
                .requestMatchers(HttpMethod.DELETE, "/api/timetables/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,    "/api/timetables/**").hasAnyRole("ADMIN", "LECTURER", "STUDENT")

                // Announcement publishing
                .requestMatchers(HttpMethod.POST, "/announcements/**").hasAnyRole("ADMIN", "LECTURER")
                .requestMatchers(HttpMethod.POST, "/api/announcements/**").hasAnyRole("ADMIN", "LECTURER")

                // Everything else requires authentication
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }
}
