package com.revplay.analytics.client;

import com.revplay.analytics.dto.ArtistDto;
import com.revplay.analytics.dto.SongDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "artist-service")
public interface ArtistServiceClient {

    @GetMapping("/api/artists/{artistId}")
    ArtistDto getArtist(@PathVariable Long artistId);

    @GetMapping("/api/artists/{artistId}/songs")
    List<SongDto> getArtistSongs(@PathVariable Long artistId);

    @GetMapping("/api/internal/songs/{songId}")
    SongDto getSong(@PathVariable Long songId);
}
