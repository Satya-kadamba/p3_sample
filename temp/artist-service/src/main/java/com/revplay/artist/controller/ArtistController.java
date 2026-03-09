package com.revplay.artist.controller;

import com.revplay.artist.dto.ArtistProfileRequest;
import com.revplay.artist.dto.ArtistProfileResponse;
import com.revplay.artist.service.ArtistService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/profile")
    public ResponseEntity<ArtistProfileResponse> getProfile(
            @RequestHeader("X-User-Id") Long userId) {
        ArtistProfileResponse response = artistService.getArtistProfile(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/profile")
    public ResponseEntity<ArtistProfileResponse> createProfile(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Role") String role,
            @Valid @RequestBody ArtistProfileRequest request) {

        if (!"ARTIST".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null);
        }

        ArtistProfileResponse response = artistService.createArtist(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/profile")
    public ResponseEntity<ArtistProfileResponse> updateProfile(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody ArtistProfileRequest request) {
        ArtistProfileResponse response = artistService.updateArtist(userId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{artistId}")
    public ResponseEntity<ArtistProfileResponse> getArtistById(
            @PathVariable Long artistId) {
        ArtistProfileResponse response = artistService.getArtistById(artistId);
        return ResponseEntity.ok(response);
    }
}
