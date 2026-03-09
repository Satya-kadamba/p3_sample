package com.revplay.player.service;

import com.revplay.player.client.ArtistServiceClient;
import com.revplay.player.dto.PlayResponse;
import com.revplay.player.dto.SongDto;
import com.revplay.player.entity.ListeningHistory;
import com.revplay.player.exception.ResourceNotFoundException;
import com.revplay.player.repository.ListeningHistoryRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PlayerService {

    private final ListeningHistoryRepository listeningHistoryRepository;
    private final ArtistServiceClient artistServiceClient;

    public PlayerService(ListeningHistoryRepository listeningHistoryRepository,
                         ArtistServiceClient artistServiceClient) {
        this.listeningHistoryRepository = listeningHistoryRepository;
        this.artistServiceClient = artistServiceClient;
    }

    @Transactional
    @CircuitBreaker(name = "artistService", fallbackMethod = "playFallback")
    public PlayResponse play(Long userId, Long songId) {
        // Get song details from artist-service
        SongDto song = artistServiceClient.getSongById(songId);

        // Create listening history record with initial duration of 0
        ListeningHistory history = ListeningHistory.builder()
                .userId(userId)
                .songId(songId)
                .playedAt(LocalDateTime.now())
                .duration(0)
                .build();

        listeningHistoryRepository.save(history);

        // Increment play count in artist-service
        try {
            artistServiceClient.incrementPlayCount(songId);
        } catch (Exception e) {
            // Log error but don't fail the play request
            System.err.println("Failed to increment play count for song " + songId + ": " + e.getMessage());
        }

        // Return play response
        return PlayResponse.builder()
                .songId(song.getId())
                .title(song.getTitle())
                .artistName(song.getArtistName())
                .albumTitle(song.getAlbumTitle())
                .fileUrl(song.getFileUrl())
                .coverImageUrl(song.getCoverImageUrl())
                .duration(song.getDuration())
                .build();
    }

    @Transactional
    public void updatePlayDuration(Long historyId, Integer duration) {
        ListeningHistory history = listeningHistoryRepository.findById(historyId)
                .orElseThrow(() -> new ResourceNotFoundException("Listening history not found with id: " + historyId));

        history.setDuration(duration);
        listeningHistoryRepository.save(history);
    }

    private PlayResponse playFallback(Long userId, Long songId, Exception e) {
        throw new ResourceNotFoundException("Song not found or artist-service unavailable: " + songId);
    }
}
