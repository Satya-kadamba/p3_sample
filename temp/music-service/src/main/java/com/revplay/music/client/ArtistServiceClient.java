package com.revplay.music.client;

import com.revplay.music.dto.PageResponse;
import com.revplay.music.dto.SongCatalogResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "artist-service", path = "/api")
public interface ArtistServiceClient {

    @GetMapping("/internal/songs/public")
    PageResponse<SongCatalogResponse> getPublicSongs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    );

    @GetMapping("/internal/songs/{songId}")
    SongCatalogResponse getSongById(@PathVariable Long songId);

    @GetMapping("/artists/{artistId}")
    Map<String, Object> getArtistInfo(@PathVariable Long artistId);
}
