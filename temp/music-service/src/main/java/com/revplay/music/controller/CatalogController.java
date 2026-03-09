package com.revplay.music.controller;

import com.revplay.music.dto.GenreResponse;
import com.revplay.music.dto.PageResponse;
import com.revplay.music.dto.SongCatalogResponse;
import com.revplay.music.service.CatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/songs")
    public ResponseEntity<PageResponse<SongCatalogResponse>> getPublicSongs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PageResponse<SongCatalogResponse> songs = catalogService.getPublicSongs(page, size);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/songs/{songId}")
    public ResponseEntity<SongCatalogResponse> getSongById(@PathVariable Long songId) {
        SongCatalogResponse song = catalogService.getSongById(songId);
        return ResponseEntity.ok(song);
    }

    @GetMapping("/genres")
    public ResponseEntity<List<GenreResponse>> getGenres() {
        List<GenreResponse> genres = catalogService.getGenres();
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/genres/{genre}/songs")
    public ResponseEntity<PageResponse<SongCatalogResponse>> getSongsByGenre(
            @PathVariable String genre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PageResponse<SongCatalogResponse> songs = catalogService.getSongsByGenre(genre, page, size);
        return ResponseEntity.ok(songs);
    }
}
