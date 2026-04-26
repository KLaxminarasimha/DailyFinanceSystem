package com.dailyfinance.api_gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtGatewayFilter implements GlobalFilter {

    private final String SECRET = "my-super-secret-key-12345678901234567890"; // 🔥 same as auth-service

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // 🔓 Skip auth endpoints
        if (path.contains("/api/v1/auth") || path.contains("/api/v1/customer")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            Long userId = Long.valueOf(claims.get("userId").toString());

            // 🔥 Inject X-USER-ID
            ServerHttpRequest request = exchange.getRequest().mutate()
                    .header("X-USER-ID", userId.toString())
                    .build();

            return chain.filter(exchange.mutate().request(request).build());

        } catch (Exception e) {
            return chain.filter(exchange);
        }
    }
}