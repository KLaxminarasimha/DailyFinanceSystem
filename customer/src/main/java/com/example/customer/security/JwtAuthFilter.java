//package com.example.customer.security;
//
//import com.example.customer.security.JwtUtil;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Collections;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthFilter extends OncePerRequestFilter {
//
//    private final JwtUtil jwtUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        // 🔹 Get Authorization header
//        String header = request.getHeader("Authorization");
//
//        // 🔹 If no token → block request
//        if (header == null || !header.startsWith("Bearer ")) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("Missing or invalid Authorization header");
//            return;
//        }
//
//        // 🔹 Extract token
//        String token = header.substring(7);
//
//        try {
//            // 🔹 Validate token
//            jwtUtil.validateToken(token);
//
//            // 🔹 Extract data from JWT
//            Long userId = jwtUtil.extractUserId(token);
//            String role = jwtUtil.extractRole(token);
//
//            // 🔹 Convert role → Spring Security format
//            SimpleGrantedAuthority authority =
//                    new SimpleGrantedAuthority("ROLE_" + role);
//
//            // 🔥 IMPORTANT: set userId as principal
//            UsernamePasswordAuthenticationToken authentication =
//                    new UsernamePasswordAuthenticationToken(
//                            userId,
//                            null,
//                            Collections.singletonList(authority)
//                    );
//
//            // 🔹 Set authentication
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        } catch (Exception e) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("Invalid or expired token");
//            return;
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}