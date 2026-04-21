package com.dailyfinance.auth_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Get request path
        String path = request.getServletPath();

        // Skip AUTH APIs (VERY IMPORTANT)
        if (path.startsWith("/api/v1/auth") ||
                path.equals("/api/v1/customer/register")) {

            filterChain.doFilter(request, response);
            return;
        }

        // Get Authorization header
        String header = request.getHeader("Authorization");

        // If no token → block request
        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing token");
            return;
        }

        //  Extract token
        String token = header.replace("Bearer ", "");

        try {
            // Validate token
            jwtUtil.validateToken(token);

            // Extract role
            String role = jwtUtil.extractRole(token);

            // Convert role → Spring format
            SimpleGrantedAuthority authority =
                    new SimpleGrantedAuthority("ROLE_" + role);

            // Set authentication
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            null,
                            null,
                            Collections.singletonList(authority)
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}