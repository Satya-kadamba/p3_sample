package com.revplay.artist.controller;

import com.revplay.artist.dto.SongResponse;
import com.revplay.artist.dto.SongUpdateRequest;
import com.revplay.artist.dto.SongUploadRequest;
import com.revplay.artist.entity.Visibility;
import com.revplay.artist.service.SongService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @PostMapping
    public ResponseEntity<SongResponse> uploadSong(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody SongUploadRequest request) {
        SongResponse response = songService.uploadSong(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SongResponse>> getSongs(
            @RequestHeader("X-User-Id") Long userId) {
        List<SongResponse> songs = songService.getSongsByArtist(userId);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/{songId}")
    public ResponseEntity<SongResponse> getSong(
            @PathVariable Long songId) {
        SongResponse response = songService.getSong(songId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{songId}")
    public ResponseEntity<SongResponse> updateSong(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long songId,
            @Valid @RequestBody SongUpdateRequest request) {
        SongResponse response = songService.updateSong(userId, songId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{songId}")
    public ResponseEntity<Void> deleteSong(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long songId) {
        songService.deleteSong(userId, songId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{songId}/visibility")
    public ResponseEntity<SongResponse> updateVisibility(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long songId,
            @RequestParam Visibility visibility) {
        SongUpdateRequest request = SongUpdateRequest.builder()
                .visibility(visibility)
                .build();
        SongResponse response = songService.updateSong(userId, songId, request);
        return ResponseEntity.ok(response);
    }
}
