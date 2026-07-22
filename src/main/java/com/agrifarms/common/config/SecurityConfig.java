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
                // ── Fully public: auth & media ───────────────────────────────
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/media/**").permitAll()

                // ── Public READ-ONLY: inventory listing (GET only) ───────────
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/inventory/**").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/users/phone/**").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/users/email/**").permitAll()

                .requestMatchers("/api/users", "/api/users/**").permitAll()
                .requestMatchers("/api/notifications", "/api/notifications/**").permitAll()
                .requestMatchers("/api/bookings", "/api/bookings/**").permitAll()
                .requestMatchers("/api/inventory", "/api/inventory/**").permitAll()
                .requestMatchers("/api/reviews", "/api/reviews/**").permitAll()
                .requestMatchers("/actuator/health/**").permitAll()
                .requestMatchers("/actuator/info").permitAll()
                // ── Everything else requires a valid JWT ─────────────────────
                .anyRequest().authenticated()
            )
            // Register our local JWT authentication filter
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setExposedHeaders(Collections.singletonList("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
