package com.revplay.artist.controller;

import com.revplay.artist.dto.AlbumRequest;
import com.revplay.artist.dto.AlbumResponse;
import com.revplay.artist.service.AlbumService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists/albums")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @PostMapping
    public ResponseEntity<AlbumResponse> createAlbum(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody AlbumRequest request) {
        AlbumResponse response = albumService.createAlbum(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AlbumResponse>> getAlbums(
            @RequestHeader("X-User-Id") Long userId) {
        List<AlbumResponse> albums = albumService.getAlbumsByArtist(userId);
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/{albumId}")
    public ResponseEntity<AlbumResponse> getAlbum(
            @PathVariable Long albumId) {
        AlbumResponse response = albumService.getAlbum(albumId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{albumId}")
    public ResponseEntity<AlbumResponse> updateAlbum(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long albumId,
            @Valid @RequestBody AlbumRequest request) {
        AlbumResponse response = albumService.updateAlbum(userId, albumId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{albumId}")
    public ResponseEntity<Void> deleteAlbum(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long albumId) {
        albumService.deleteAlbum(userId, albumId);
        return ResponseEntity.noContent().build();
    }
}
