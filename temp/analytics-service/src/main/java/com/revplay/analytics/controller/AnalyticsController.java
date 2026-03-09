package com.revplay.analytics.controller;

import com.revplay.analytics.config.SecurityConfig;
import com.revplay.analytics.dto.*;
import com.revplay.analytics.service.AnalyticsService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private static final Logger logger = LoggerFactory.getLogger(AnalyticsController.class);

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/artist/{artistId}/overview")
    public ResponseEntity<AnalyticsOverviewResponse> getArtistOverview(
            @PathVariable Long artistId,
            @RequestParam(defaultValue = "ALL_TIME") String period,
            HttpServletRequest request
    ) {
        logger.info("GET /api/analytics/artist/{}/overview - period: {}", artistId, period);

        Long userId = (Long) request.getAttribute("userId");
        String userRole = (String) request.getAttribute("userRole");

        SecurityConfig.validateArtistAccess(userId, userRole, artistId);

        AnalyticsOverviewResponse overview = analyticsService.getArtistOverview(artistId, period);
        return ResponseEntity.ok(overview);
    }

    @GetMapping("/artist/{artistId}/songs")
    public ResponseEntity<List<SongPerformanceResponse>> getSongPerformance(
            @PathVariable Long artistId,
            HttpServletRequest request
    ) {
        logger.info("GET /api/analytics/artist/{}/songs", artistId);

        Long userId = (Long) request.getAttribute("userId");
        String userRole = (String) request.getAttribute("userRole");

        SecurityConfig.validateArtistAccess(userId, userRole, artistId);

        List<SongPerformanceResponse> performance = analyticsService.getSongPerformance(artistId);
        return ResponseEntity.ok(performance);
    }

    @GetMapping("/artist/{artistId}/top")
    public ResponseEntity<TopSongsResponse> getTopSongs(
            @PathVariable Long artistId,
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            @RequestParam(required = false, defaultValue = "ALL_TIME") String period,
            HttpServletRequest request
    ) {
        logger.info("GET /api/analytics/artist/{}/top - limit: {}, period: {}", artistId, limit, period);

        Long userId = (Long) request.getAttribute("userId");
        String userRole = (String) request.getAttribute("userRole");

        SecurityConfig.validateArtistAccess(userId, userRole, artistId);

        if (limit <= 0 || limit > 100) {
            throw new IllegalArgumentException("Limit must be between 1 and 100");
        }

        TopSongsResponse topSongs = analyticsService.getTopSongs(artistId, limit, period);
        return ResponseEntity.ok(topSongs);
    }

    @GetMapping("/artist/{artistId}/trends")
    public ResponseEntity<ListeningTrendResponse> getListeningTrends(
            @PathVariable Long artistId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            HttpServletRequest request
    ) {
        logger.info("GET /api/analytics/artist/{}/trends - startDate: {}, endDate: {}",
                artistId, startDate, endDate);

        Long userId = (Long) request.getAttribute("userId");
        String userRole = (String) request.getAttribute("userRole");

        SecurityConfig.validateArtistAccess(userId, userRole, artistId);

        // Default to last 30 days if not specified
        if (startDate == null) {
            startDate = LocalDate.now().minusDays(30);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        if (startDate.isBefore(LocalDate.now().minusYears(1))) {
            throw new IllegalArgumentException("Start date cannot be more than 1 year in the past");
        }

        ListeningTrendResponse trends = analyticsService.getListeningTrends(artistId, startDate, endDate);
        return ResponseEntity.ok(trends);
    }

    @GetMapping("/artist/{artistId}/listeners")
    public ResponseEntity<List<TopListenerResponse>> getTopListeners(
            @PathVariable Long artistId,
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            HttpServletRequest request
    ) {
        logger.info("GET /api/analytics/artist/{}/listeners - limit: {}", artistId, limit);

        Long userId = (Long) request.getAttribute("userId");
        String userRole = (String) request.getAttribute("userRole");

        SecurityConfig.validateArtistAccess(userId, userRole, artistId);

        if (limit <= 0 || limit > 100) {
            throw new IllegalArgumentException("Limit must be between 1 and 100");
        }

        List<TopListenerResponse> listeners = analyticsService.getTopListeners(artistId, limit);
        return ResponseEntity.ok(listeners);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Analytics Service is running");
    }
}
