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
        String path = request.getURI().getPath();

        // ✅ Allow preflight
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return chain.filter(exchange);
        }

        // ✅ PUBLIC ENDPOINTS
        if (path.startsWith("/api/v1/auth") ||
                path.startsWith("/api/v1/customer")) {
            return chain.filter(exchange);
        }

        // 🔒 PROTECTED
        String authHeader = request.getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return onError(exchange, HttpStatus.UNAUTHORIZED);
        }

        try {
            String token = authHeader.substring(7);

            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            String userId = claims.get("userId").toString();

            ServerHttpRequest mutated = request.mutate()
                    .header("X-USER-ID", userId)
                    .build();

            return chain.filter(exchange.mutate().request(mutated).build());

        } catch (Exception e) {
            return onError(exchange, HttpStatus.UNAUTHORIZED);
        }
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        return exchange.getResponse().setComplete();
    }
}