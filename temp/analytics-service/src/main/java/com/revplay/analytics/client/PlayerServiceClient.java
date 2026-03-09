package com.revplay.analytics.client;

import com.revplay.analytics.dto.ListenerStatsDto;
import com.revplay.analytics.dto.PlayHistoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "player-service")
public interface PlayerServiceClient {

    @GetMapping("/api/internal/history/artist/{artistId}/listeners")
    List<ListenerStatsDto> getArtistListeners(
            @PathVariable Long artistId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    );

    @GetMapping("/api/internal/history/song/{songId}")
    List<PlayHistoryDto> getSongPlayHistory(
            @PathVariable Long songId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    );

    @GetMapping("/api/internal/history/trends")
    List<PlayHistoryDto> getPlayTrends(
            @RequestParam Long artistId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    );
}
