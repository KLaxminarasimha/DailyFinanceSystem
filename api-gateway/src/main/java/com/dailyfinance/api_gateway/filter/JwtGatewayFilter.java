package com.dailyfinance.api_gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.cloud.gateway.filter.*;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtGatewayFilter implements GlobalFilter {

    private final String SECRET = "my-super-secret-key-12345678901234567890";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            return chain.filter(exchange);
        }

        String token = request.getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION)
                .replace("Bearer ", "");

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        // 🔥 FIX HERE
        Long userId = claims.get("userId", Long.class);

        ServerHttpRequest mutated = request.mutate()
                .header("X-USER-ID", String.valueOf(userId))
                .build();

        return chain.filter(exchange.mutate().request(mutated).build());
    }
}