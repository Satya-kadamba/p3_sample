package com.revplay.analytics.config;

import com.revplay.analytics.exception.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public UserContextFilter userContextFilter() {
        return new UserContextFilter();
    }

    public static class UserContextFilter extends OncePerRequestFilter {

        private static final Logger filterLogger = LoggerFactory.getLogger(UserContextFilter.class);

        @Override
        protected void doFilterInternal(
                HttpServletRequest request,
                HttpServletResponse response,
                FilterChain filterChain
        ) throws ServletException, IOException {

            String userId = request.getHeader("X-User-Id");
            String role = request.getHeader("X-User-Role");

            filterLogger.debug("Processing request with userId: {}, role: {}", userId, role);

            // Store in request attributes for controller access
            if (userId != null) {
                request.setAttribute("userId", Long.parseLong(userId));
            }
            if (role != null) {
                request.setAttribute("userRole", role);
            }

            filterChain.doFilter(request, response);
        }
    }

    public static void validateArtistAccess(Long requestingUserId, String userRole, Long artistId) {
        logger.debug("Validating artist access: userId={}, role={}, artistId={}",
                requestingUserId, userRole, artistId);

        if (!"ARTIST".equalsIgnoreCase(userRole)) {
            throw new UnauthorizedException("Only artists can access analytics data");
        }

        // Artists can only view their own analytics
        // Assuming artistId matches userId for artists
        if (!requestingUserId.equals(artistId)) {
            throw new UnauthorizedException("You can only view analytics for your own artist profile");
        }
    }
}
