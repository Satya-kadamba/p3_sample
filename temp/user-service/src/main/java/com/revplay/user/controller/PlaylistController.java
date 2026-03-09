package com.revplay.user.controller;

import com.revplay.user.dto.PlaylistRequest;
import com.revplay.user.dto.PlaylistResponse;
import com.revplay.user.dto.PlaylistSongRequest;
import com.revplay.user.service.PlaylistService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    public ResponseEntity<PlaylistResponse> createPlaylist(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody PlaylistRequest request) {
        PlaylistResponse playlist = playlistService.createPlaylist(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(playlist);
    }

    @GetMapping
    public ResponseEntity<List<PlaylistResponse>> getPlaylists(@RequestHeader("X-User-Id") Long userId) {
        List<PlaylistResponse> playlists = playlistService.getPlaylists(userId);
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponse> getPlaylist(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id) {
        PlaylistResponse playlist = playlistService.getPlaylist(userId, id);
        return ResponseEntity.ok(playlist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistResponse> updatePlaylist(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id,
            @Valid @RequestBody PlaylistRequest request) {
        PlaylistResponse playlist = playlistService.updatePlaylist(userId, id, request);
        return ResponseEntity.ok(playlist);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id) {
        playlistService.deletePlaylist(userId, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/songs")
    public ResponseEntity<Void> addSongToPlaylist(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id,
            @Valid @RequestBody PlaylistSongRequest request) {
        playlistService.addSong(userId, id, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}/songs/{songId}")
    public ResponseEntity<Void> removeSongFromPlaylist(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id,
            @PathVariable Long songId) {
        playlistService.removeSong(userId, id, songId);
        return ResponseEntity.noContent().build();
    }
}
