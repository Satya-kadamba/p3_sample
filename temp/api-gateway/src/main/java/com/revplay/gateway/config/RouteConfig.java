package com.revplay.gateway.config;

import com.revplay.gateway.filter.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public RouteConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Auth Service - No JWT required
                .route("auth-service", r -> r.path("/api/auth/**")
                        .uri("lb://auth-service"))

                // User Service - JWT required
                .route("user-service", r -> r.path("/api/users/**", "/api/playlists/**", "/api/favorites/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("lb://user-service"))

                // Artist Service - JWT required
                .route("artist-service", r -> r.path("/api/artists/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("lb://artist-service"))

                // Music Service - Public endpoints
                .route("music-service", r -> r.path("/api/catalog/**")
                        .uri("lb://music-service"))

                // Player Service - JWT required
                .route("player-service", r -> r.path("/api/player/**", "/api/history/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("lb://player-service"))

                // Analytics Service - JWT required
                .route("analytics-service", r -> r.path("/api/analytics/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("lb://analytics-service"))

                .build();
    }
}
