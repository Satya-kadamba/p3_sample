package com.revplay.player.service;

import com.revplay.player.client.ArtistServiceClient;
import com.revplay.player.dto.ListeningHistoryResponse;
import com.revplay.player.dto.SongDto;
import com.revplay.player.dto.UserHistoryStatsResponse;
import com.revplay.player.entity.ListeningHistory;
import com.revplay.player.repository.ListeningHistoryRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ListeningHistoryService {

    private final ListeningHistoryRepository listeningHistoryRepository;
    private final ArtistServiceClient artistServiceClient;

    public ListeningHistoryService(ListeningHistoryRepository listeningHistoryRepository,
                                    ArtistServiceClient artistServiceClient) {
        this.listeningHistoryRepository = listeningHistoryRepository;
        this.artistServiceClient = artistServiceClient;
    }

    @CircuitBreaker(name = "artistService", fallbackMethod = "getRecentHistoryFallback")
    public Page<ListeningHistoryResponse> getRecentHistory(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ListeningHistory> historyPage = listeningHistoryRepository.findByUserIdOrderByPlayedAtDesc(userId, pageable);

        return historyPage.map(history -> {
            try {
                SongDto song = artistServiceClient.getSongById(history.getSongId());
                return ListeningHistoryResponse.builder()
                        .id(history.getId())
                        .songId(history.getSongId())
                        .songTitle(song.getTitle())
                        .artistName(song.getArtistName())
                        .albumTitle(song.getAlbumTitle())
                        .coverImageUrl(song.getCoverImageUrl())
                        .playedAt(history.getPlayedAt())
                        .listenedDuration(history.getDuration())
                        .build();
            } catch (Exception e) {
                // Fallback: return history without song details
                return ListeningHistoryResponse.builder()
                        .id(history.getId())
                        .songId(history.getSongId())
                        .songTitle("Unknown")
                        .artistName("Unknown")
                        .albumTitle("Unknown")
                        .coverImageUrl(null)
                        .playedAt(history.getPlayedAt())
                        .listenedDuration(history.getDuration())
                        .build();
            }
        });
    }

    @Transactional
    public void clearHistory(Long userId) {
        listeningHistoryRepository.deleteByUserId(userId);
    }

    public UserHistoryStatsResponse getStats(Long userId) {
        long totalSongsPlayed = listeningHistoryRepository.countByUserId(userId);
        Long totalListeningTime = listeningHistoryRepository.sumDurationByUserId(userId);
        if (totalListeningTime == null) {
            totalListeningTime = 0L;
        }

        List<Long> uniqueSongIds = listeningHistoryRepository.findDistinctSongIdsByUserId(userId);
        long uniqueSongsPlayed = uniqueSongIds.size();

        // For now, return empty top genres (would need genre data from artist-service)
        List<String> topGenres = new ArrayList<>();

        return UserHistoryStatsResponse.builder()
                .totalSongsPlayed(totalSongsPlayed)
                .totalListeningTimeSeconds(totalListeningTime)
                .uniqueSongsPlayed(uniqueSongsPlayed)
                .topGenres(topGenres)
                .build();
    }

    public List<ListeningHistoryResponse> getHistoryByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        List<ListeningHistory> historyList = listeningHistoryRepository.findByUserIdAndPlayedAtBetween(userId, startDate, endDate);

        return historyList.stream().map(history -> {
            try {
                SongDto song = artistServiceClient.getSongById(history.getSongId());
                return ListeningHistoryResponse.builder()
                        .id(history.getId())
                        .songId(history.getSongId())
                        .songTitle(song.getTitle())
                        .artistName(song.getArtistName())
                        .albumTitle(song.getAlbumTitle())
                        .coverImageUrl(song.getCoverImageUrl())
                        .playedAt(history.getPlayedAt())
                        .listenedDuration(history.getDuration())
                        .build();
            } catch (Exception e) {
                return ListeningHistoryResponse.builder()
                        .id(history.getId())
                        .songId(history.getSongId())
                        .songTitle("Unknown")
                        .artistName("Unknown")
                        .albumTitle("Unknown")
                        .coverImageUrl(null)
                        .playedAt(history.getPlayedAt())
                        .listenedDuration(history.getDuration())
                        .build();
            }
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getTopSongsByUser(Long userId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<Object[]> results = listeningHistoryRepository.findTopSongsByUserId(userId, pageable);

        return results.stream().map(result -> {
            Long songId = (Long) result[0];
            Long playCount = (Long) result[1];

            Map<String, Object> songStats = new HashMap<>();
            songStats.put("songId", songId);
            songStats.put("playCount", playCount);

            try {
                SongDto song = artistServiceClient.getSongById(songId);
                songStats.put("title", song.getTitle());
                songStats.put("artistName", song.getArtistName());
                songStats.put("albumTitle", song.getAlbumTitle());
            } catch (Exception e) {
                songStats.put("title", "Unknown");
                songStats.put("artistName", "Unknown");
                songStats.put("albumTitle", "Unknown");
            }

            return songStats;
        }).collect(Collectors.toList());
    }

    public List<ListeningHistory> getHistoryBySongId(Long songId) {
        return listeningHistoryRepository.findBySongId(songId);
    }

    public List<ListeningHistory> getHistoryByUserId(Long userId) {
        return listeningHistoryRepository.findByUserId(userId);
    }

    public Map<String, Long> getListenerCountsForSongs(List<Long> songIds) {
        List<Object[]> results = listeningHistoryRepository.findListenerCountsBySongIds(songIds);

        Map<String, Long> listenerCounts = new HashMap<>();
        for (Object[] result : results) {
            Long songId = (Long) result[0];
            Long listenerCount = (Long) result[1];
            listenerCounts.put(songId.toString(), listenerCount);
        }

        return listenerCounts;
    }

    public List<Map<String, Object>> getDailyTrends(LocalDateTime startDate, LocalDateTime endDate) {
        List<Object[]> results = listeningHistoryRepository.findDailyPlayCounts(startDate, endDate);

        return results.stream().map(result -> {
            Map<String, Object> trend = new HashMap<>();
            trend.put("date", result[0]);
            trend.put("playCount", result[1]);
            return trend;
        }).collect(Collectors.toList());
    }

    private Page<ListeningHistoryResponse> getRecentHistoryFallback(Long userId, int page, int size, Exception e) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ListeningHistory> historyPage = listeningHistoryRepository.findByUserIdOrderByPlayedAtDesc(userId, pageable);

        return historyPage.map(history -> ListeningHistoryResponse.builder()
                .id(history.getId())
                .songId(history.getSongId())
                .songTitle("Unknown")
                .artistName("Unknown")
                .albumTitle("Unknown")
                .coverImageUrl(null)
                .playedAt(history.getPlayedAt())
                .listenedDuration(history.getDuration())
                .build());
    }
}
