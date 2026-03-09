package com.revplay.player.controller;

import com.revplay.player.dto.NowPlayingResponse;
import com.revplay.player.dto.PlayRequest;
import com.revplay.player.dto.PlayResponse;
import com.revplay.player.entity.ListeningHistory;
import com.revplay.player.service.ListeningHistoryService;
import com.revplay.player.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    private final PlayerService playerService;
    private final ListeningHistoryService listeningHistoryService;

    public PlayerController(PlayerService playerService, ListeningHistoryService listeningHistoryService) {
        this.playerService = playerService;
        this.listeningHistoryService = listeningHistoryService;
    }

    @PostMapping("/play")
    public ResponseEntity<PlayResponse> play(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody PlayRequest request) {
        PlayResponse response = playerService.play(userId, request.getSongId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/play/{historyId}/duration")
    public ResponseEntity<Map<String, String>> updatePlayDuration(
            @PathVariable Long historyId,
            @RequestBody Map<String, Integer> request) {
        Integer duration = request.get("duration");
        if (duration == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Duration is required"));
        }

        playerService.updatePlayDuration(historyId, duration);
        return ResponseEntity.ok(Map.of("message", "Duration updated successfully"));
    }

    @GetMapping("/now-playing")
    public ResponseEntity<NowPlayingResponse> getNowPlaying(
            @RequestHeader("X-User-Id") Long userId) {
        // Get the most recent listening history entry for the user
        List<ListeningHistory> recentHistory = listeningHistoryService.getHistoryByUserId(userId);

        if (recentHistory.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Get the most recent entry
        ListeningHistory latest = recentHistory.stream()
                .max((h1, h2) -> h1.getPlayedAt().compareTo(h2.getPlayedAt()))
                .orElse(null);

        if (latest == null) {
            return ResponseEntity.noContent().build();
        }

        // For simplicity, we'll just return the song ID and played at time
        // In a real implementation, you'd check if it's currently playing
        NowPlayingResponse response = NowPlayingResponse.builder()
                .songId(latest.getSongId())
                .title("Currently Playing")
                .artistName("Artist")
                .albumTitle("Album")
                .coverImageUrl(null)
                .startedAt(latest.getPlayedAt())
                .build();

        return ResponseEntity.ok(response);
    }
}
