package com.agrifarms.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // ── Fully public: auth & media ──────────────────────────────────
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/media/**").permitAll()

                // ── Public READ-ONLY: inventory listing (GET only, unauthenticated browse) ──
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/inventory/**").permitAll()

                // ── Public: user lookup by phone/email (needed for login check) ──
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/users/phone/**").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/users/email/**").permitAll()

                // ── Public: actuator health check ───────────────────────────────
                .requestMatchers("/actuator/health/**").permitAll()
                .requestMatchers("/actuator/info").permitAll()

                // ── EVERYTHING ELSE REQUIRES A VALID JWT ────────────────────────
                // This covers: /api/users (write), /api/bookings, /api/notifications,
                // /api/reviews, /api/inventory (write), /api/users/{id}/delete, etc.
                .anyRequest().authenticated()
            )
            // Register our local JWT authentication filter
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // ── Whitelist specific origins — wildcard (*) with credentials is a security risk ──
        configuration.setAllowedOriginPatterns(Arrays.asList(
            "https://agrifarms.in",
            "https://admin.agrifarms.in",
            "https://www.agrifarms.in",
            "http://localhost:3000",    // local web dev
            "http://localhost:8080"     // local backend dev
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setExposedHeaders(Collections.singletonList("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
