package com.revplay.user.controller;

import com.revplay.user.dto.FavoriteResponse;
import com.revplay.user.service.FavoriteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/{songId}")
    public ResponseEntity<Void> addFavorite(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long songId) {
        favoriteService.addFavorite(userId, songId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{songId}")
    public ResponseEntity<Void> removeFavorite(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long songId) {
        favoriteService.removeFavorite(userId, songId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FavoriteResponse>> getFavorites(@RequestHeader("X-User-Id") Long userId) {
        List<FavoriteResponse> favorites = favoriteService.getFavorites(userId);
        return ResponseEntity.ok(favorites);
    }

    @GetMapping("/{songId}/check")
    public ResponseEntity<Map<String, Boolean>> checkFavorite(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long songId) {
        boolean isFavorite = favoriteService.isFavorite(userId, songId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isFavorite", isFavorite);
        return ResponseEntity.ok(response);
    }
}
