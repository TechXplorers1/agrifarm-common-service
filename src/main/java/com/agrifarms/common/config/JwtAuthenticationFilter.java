package com.agrifarms.common.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7).trim();
            try {
                Claims claims = jwtUtil.validateAndExtractClaims(token);

                // Check expiry explicitly
                java.util.Date expiry = claims.getExpiration();
                if (expiry != null && expiry.before(new java.util.Date())) {
                    logger.warn("[JWT] Token is expired. Expiry: " + expiry);
                    filterChain.doFilter(request, response);
                    return;
                }

                String userId = claims.getSubject();
                String role = claims.get("role", String.class);
                if (role == null) role = "Farmer";

                logger.info("[JWT] Token valid for userId=" + userId + " role=" + role
                        + " | " + request.getMethod() + " " + request.getRequestURI());

                List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())
                );

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userId, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (SignatureException e) {
                logger.error("[JWT] SIGNATURE MISMATCH — JWT_SECRET used to sign token does not match backend secret! " + e.getMessage());
            } catch (MalformedJwtException e) {
                logger.error("[JWT] Malformed token (bad format): " + e.getMessage());
            } catch (ExpiredJwtException e) {
                logger.error("[JWT] Token expired at: " + e.getClaims().getExpiration());
            } catch (UnsupportedJwtException e) {
                logger.error("[JWT] Unsupported JWT algorithm/type: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                logger.error("[JWT] Token is null, empty or whitespace: " + e.getMessage());
            } catch (Exception e) {
                logger.error("[JWT] Unexpected validation error: " + e.getClass().getSimpleName() + " — " + e.getMessage());
            }
        } else {
            String uri = request.getRequestURI();
            if (!uri.startsWith("/api/auth") && !uri.startsWith("/api/media")
                    && !uri.startsWith("/actuator") && !uri.contains("/phone/") && !uri.contains("/email/")) {
                logger.debug("[JWT] No Bearer token for protected endpoint: "
                        + request.getMethod() + " " + uri);
            }
        }

        filterChain.doFilter(request, response);
    }
}

