package com.revplay.player.controller;

import com.revplay.player.entity.ListeningHistory;
import com.revplay.player.service.ListeningHistoryService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/internal/history")
public class InternalController {

    private final ListeningHistoryService listeningHistoryService;

    public InternalController(ListeningHistoryService listeningHistoryService) {
        this.listeningHistoryService = listeningHistoryService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ListeningHistory>> getUserHistory(@PathVariable Long userId) {
        List<ListeningHistory> history = listeningHistoryService.getHistoryByUserId(userId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/song/{songId}")
    public ResponseEntity<List<ListeningHistory>> getSongHistory(@PathVariable Long songId) {
        List<ListeningHistory> history = listeningHistoryService.getHistoryBySongId(songId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/artist/{artistId}/listeners")
    public ResponseEntity<Map<String, Long>> getArtistListeners(
            @PathVariable Long artistId,
            @RequestBody List<Long> songIds) {
        // Get listener counts for all songs by this artist
        Map<String, Long> listenerCounts = listeningHistoryService.getListenerCountsForSongs(songIds);
        return ResponseEntity.ok(listenerCounts);
    }

    @GetMapping("/trends")
    public ResponseEntity<List<Map<String, Object>>> getTrends(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Map<String, Object>> trends = listeningHistoryService.getDailyTrends(startDate, endDate);
        return ResponseEntity.ok(trends);
    }
}
