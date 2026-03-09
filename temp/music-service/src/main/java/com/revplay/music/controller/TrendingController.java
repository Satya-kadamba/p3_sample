package com.revplay.music.controller;

import com.revplay.music.dto.TrendingResponse;
import com.revplay.music.service.TrendingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/catalog")
public class TrendingController {

    private final TrendingService trendingService;

    public TrendingController(TrendingService trendingService) {
        this.trendingService = trendingService;
    }

    @GetMapping("/trending")
    public ResponseEntity<TrendingResponse> getTrendingSongs(
            @RequestParam(defaultValue = "20") int limit) {
        // Default to WEEKLY if not specified
        TrendingResponse trending = trendingService.getTrendingSongs(
                TrendingResponse.TrendingPeriod.WEEKLY, limit);
        return ResponseEntity.ok(trending);
    }

    @GetMapping("/trending/{period}")
    public ResponseEntity<TrendingResponse> getTrendingByPeriod(
            @PathVariable String period,
            @RequestParam(defaultValue = "20") int limit) {
        TrendingResponse.TrendingPeriod trendingPeriod;
        try {
            trendingPeriod = TrendingResponse.TrendingPeriod.valueOf(period.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Default to WEEKLY if invalid period
            trendingPeriod = TrendingResponse.TrendingPeriod.WEEKLY;
        }

        TrendingResponse trending = trendingService.getTrendingSongs(trendingPeriod, limit);
        return ResponseEntity.ok(trending);
    }
}
