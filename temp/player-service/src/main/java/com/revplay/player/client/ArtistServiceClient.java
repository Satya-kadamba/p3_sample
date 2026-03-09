package com.revplay.player.client;

import com.revplay.player.dto.SongDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "artist-service")
public interface ArtistServiceClient {

    @GetMapping("/api/internal/songs/{songId}")
    SongDto getSongById(@PathVariable("songId") Long songId);

    @PostMapping("/api/internal/songs/{songId}/play")
    void incrementPlayCount(@PathVariable("songId") Long songId);
}
