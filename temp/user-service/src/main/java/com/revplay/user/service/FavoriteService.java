package com.revplay.user.service;

import com.revplay.user.client.ArtistServiceClient;
import com.revplay.user.dto.FavoriteResponse;
import com.revplay.user.dto.SongDto;
import com.revplay.user.entity.Favorite;
import com.revplay.user.exception.ResourceNotFoundException;
import com.revplay.user.repository.FavoriteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ArtistServiceClient artistServiceClient;

    public FavoriteService(FavoriteRepository favoriteRepository,
                          ArtistServiceClient artistServiceClient) {
        this.favoriteRepository = favoriteRepository;
        this.artistServiceClient = artistServiceClient;
    }

    @Transactional
    public void addFavorite(Long userId, Long songId) {
        // Check if already favorited
        if (favoriteRepository.existsByUserIdAndSongId(userId, songId)) {
            throw new IllegalStateException("Song already in favorites");
        }

        // Verify song exists in artist service
        try {
            artistServiceClient.getSongById(songId);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Song not found with id: " + songId);
        }

        Favorite favorite = Favorite.builder()
                .userId(userId)
                .songId(songId)
                .build();

        favoriteRepository.save(favorite);
    }

    @Transactional
    public void removeFavorite(Long userId, Long songId) {
        Favorite favorite = favoriteRepository.findByUserIdAndSongId(userId, songId)
                .orElseThrow(() -> new ResourceNotFoundException("Favorite not found for songId: " + songId));

        favoriteRepository.delete(favorite);
    }

    @Transactional(readOnly = true)
    public List<FavoriteResponse> getFavorites(Long userId) {
        List<Favorite> favorites = favoriteRepository.findByUserId(userId);

        if (favorites.isEmpty()) {
            return new ArrayList<>();
        }

        List<Long> songIds = favorites.stream()
                .map(Favorite::getSongId)
                .collect(Collectors.toList());

        List<SongDto> songs;
        try {
            songs = artistServiceClient.getSongsByIds(songIds);
        } catch (Exception e) {
            // If artist service is down, return favorites without song details
            System.err.println("Failed to fetch song details: " + e.getMessage());
            return favorites.stream()
                    .map(favorite -> FavoriteResponse.builder()
                            .id(favorite.getId())
                            .songId(favorite.getSongId())
                            .songTitle("Unavailable")
                            .artistName("Unavailable")
                            .addedAt(favorite.getAddedAt())
                            .build())
                    .collect(Collectors.toList());
        }

        Map<Long, SongDto> songMap = songs.stream()
                .collect(Collectors.toMap(SongDto::getId, song -> song));

        return favorites.stream()
                .map(favorite -> {
                    SongDto song = songMap.get(favorite.getSongId());
                    return FavoriteResponse.builder()
                            .id(favorite.getId())
                            .songId(favorite.getSongId())
                            .songTitle(song != null ? song.getTitle() : "Unknown")
                            .artistName(song != null ? song.getArtistName() : "Unknown")
                            .addedAt(favorite.getAddedAt())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean isFavorite(Long userId, Long songId) {
        return favoriteRepository.existsByUserIdAndSongId(userId, songId);
    }
}
