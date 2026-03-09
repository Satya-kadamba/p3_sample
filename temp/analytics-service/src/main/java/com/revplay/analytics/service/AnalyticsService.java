package com.revplay.analytics.service;

import com.revplay.analytics.client.ArtistServiceClient;
import com.revplay.analytics.client.PlayerServiceClient;
import com.revplay.analytics.client.UserServiceClient;
import com.revplay.analytics.dto.*;
import com.revplay.analytics.exception.ServiceUnavailableException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private static final Logger logger = LoggerFactory.getLogger(AnalyticsService.class);

    private final PlayerServiceClient playerServiceClient;
    private final ArtistServiceClient artistServiceClient;
    private final UserServiceClient userServiceClient;
    private final AggregationService aggregationService;

    public AnalyticsService(
            PlayerServiceClient playerServiceClient,
            ArtistServiceClient artistServiceClient,
            UserServiceClient userServiceClient,
            AggregationService aggregationService
    ) {
        this.playerServiceClient = playerServiceClient;
        this.artistServiceClient = artistServiceClient;
        this.userServiceClient = userServiceClient;
        this.aggregationService = aggregationService;
    }

    @Cacheable(value = "artistOverview", key = "#artistId + '_' + #period")
    public AnalyticsOverviewResponse getArtistOverview(Long artistId, String period) {
        logger.info("Getting artist overview for artist {} in period {}", artistId, period);

        try {
            LocalDate[] dateRange = getDateRangeForPeriod(period);
            List<PlayHistoryDto> plays = playerServiceClient.getPlayTrends(
                    artistId,
                    dateRange[0],
                    dateRange[1]
            );

            Long totalPlays = aggregationService.countTotalPlays(plays);
            Long uniqueListeners = aggregationService.countUniqueListeners(plays);
            Double totalListeningTime = aggregationService.calculateTotalListeningTime(plays);
            Double avgDuration = aggregationService.calculateAveragePlayDuration(plays);

            // Find top song
            Map<Long, Long> songPlayCounts = plays.stream()
                    .collect(Collectors.groupingBy(
                            PlayHistoryDto::getSongId,
                            Collectors.counting()
                    ));

            Long topSongId = null;
            String topSongTitle = null;
            long maxPlays = 0;

            for (Map.Entry<Long, Long> entry : songPlayCounts.entrySet()) {
                if (entry.getValue() > maxPlays) {
                    maxPlays = entry.getValue();
                    topSongId = entry.getKey();
                }
            }

            if (topSongId != null) {
                try {
                    SongDto song = artistServiceClient.getSong(topSongId);
                    topSongTitle = song.getTitle();
                } catch (FeignException e) {
                    logger.warn("Failed to fetch top song details: {}", e.getMessage());
                }
            }

            return AnalyticsOverviewResponse.builder()
                    .totalPlays(totalPlays)
                    .uniqueListeners(uniqueListeners)
                    .totalListeningTimeMinutes(totalListeningTime)
                    .averagePlayDuration(avgDuration)
                    .topSongId(topSongId)
                    .topSongTitle(topSongTitle)
                    .period(period)
                    .build();

        } catch (FeignException e) {
            logger.error("Failed to fetch play data from player-service", e);
            throw new ServiceUnavailableException("Player service is currently unavailable");
        }
    }

    @Cacheable(value = "songPerformance", key = "#artistId")
    public List<SongPerformanceResponse> getSongPerformance(Long artistId) {
        logger.info("Getting song performance for artist {}", artistId);

        try {
            // Get all songs for the artist
            List<SongDto> songs = artistServiceClient.getArtistSongs(artistId);

            List<SongPerformanceResponse> performances = new ArrayList<>();

            for (SongDto song : songs) {
                try {
                    List<PlayHistoryDto> plays = playerServiceClient.getSongPlayHistory(
                            song.getId(),
                            null,
                            null
                    );

                    long playCount = plays.size();
                    long uniqueListeners = plays.stream()
                            .map(PlayHistoryDto::getUserId)
                            .distinct()
                            .count();

                    double avgDuration = plays.isEmpty() ? 0.0 :
                            plays.stream()
                                    .mapToDouble(PlayHistoryDto::getListenDurationSeconds)
                                    .average()
                                    .orElse(0.0);

                    double completionRate = aggregationService.calculateCompletionRate(song.getId());

                    performances.add(SongPerformanceResponse.builder()
                            .songId(song.getId())
                            .title(song.getTitle())
                            .playCount(playCount)
                            .uniqueListeners(uniqueListeners)
                            .averageListenDuration(Math.round(avgDuration * 100.0) / 100.0)
                            .completionRate(completionRate)
                            .build());

                } catch (FeignException e) {
                    logger.warn("Failed to fetch play history for song {}: {}", song.getId(), e.getMessage());
                    // Continue with next song
                }
            }

            // Sort by play count descending
            performances.sort((a, b) -> Long.compare(b.getPlayCount(), a.getPlayCount()));

            return performances;

        } catch (FeignException e) {
            logger.error("Failed to fetch artist songs", e);
            throw new ServiceUnavailableException("Artist service is currently unavailable");
        }
    }

    @Cacheable(value = "topSongs", key = "#artistId + '_' + #limit")
    public TopSongsResponse getTopSongs(Long artistId, Integer limit, String period) {
        logger.info("Getting top {} songs for artist {} in period {}", limit, artistId, period);

        List<SongPerformanceResponse> allSongs = getSongPerformance(artistId);

        List<SongPerformanceResponse> topSongs = allSongs.stream()
                .limit(limit != null ? limit : 10)
                .collect(Collectors.toList());

        return TopSongsResponse.builder()
                .songs(topSongs)
                .period(period != null ? period : "ALL_TIME")
                .artistId(artistId)
                .build();
    }

    @Cacheable(value = "listeningTrends", key = "#artistId + '_' + #startDate + '_' + #endDate")
    public ListeningTrendResponse getListeningTrends(Long artistId, LocalDate startDate, LocalDate endDate) {
        logger.info("Getting listening trends for artist {} from {} to {}", artistId, startDate, endDate);

        try {
            List<DailyTrendData> trends = aggregationService.aggregateTrends(artistId, startDate, endDate);

            return ListeningTrendResponse.builder()
                    .trends(trends)
                    .build();

        } catch (FeignException e) {
            logger.error("Failed to fetch trend data", e);
            throw new ServiceUnavailableException("Player service is currently unavailable");
        }
    }

    @Cacheable(value = "topListeners", key = "#artistId + '_' + #limit")
    public List<TopListenerResponse> getTopListeners(Long artistId, Integer limit) {
        logger.info("Getting top {} listeners for artist {}", limit, artistId);

        try {
            List<ListenerStatsDto> listeners = playerServiceClient.getArtistListeners(
                    artistId,
                    null,
                    null
            );

            // Sort by play count descending
            listeners.sort((a, b) -> Long.compare(b.getPlayCount(), a.getPlayCount()));

            // Limit results
            List<ListenerStatsDto> topListeners = listeners.stream()
                    .limit(limit != null ? limit : 10)
                    .collect(Collectors.toList());

            // Enrich with user information
            List<TopListenerResponse> responses = new ArrayList<>();

            for (ListenerStatsDto listener : topListeners) {
                try {
                    UserDto user = userServiceClient.getUser(listener.getUserId());

                    responses.add(TopListenerResponse.builder()
                            .userId(user.getId())
                            .username(user.getUsername())
                            .playCount(listener.getPlayCount())
                            .totalListeningMinutes(listener.getTotalListeningMinutes())
                            .build());

                } catch (FeignException e) {
                    logger.warn("Failed to fetch user details for user {}: {}", listener.getUserId(), e.getMessage());
                    // Add without username enrichment
                    responses.add(TopListenerResponse.builder()
                            .userId(listener.getUserId())
                            .username("Unknown")
                            .playCount(listener.getPlayCount())
                            .totalListeningMinutes(listener.getTotalListeningMinutes())
                            .build());
                }
            }

            return responses;

        } catch (FeignException e) {
            logger.error("Failed to fetch listener data", e);
            throw new ServiceUnavailableException("Player service is currently unavailable");
        }
    }

    private LocalDate[] getDateRangeForPeriod(String period) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate;

        switch (period.toUpperCase()) {
            case "DAILY":
                startDate = endDate.minusDays(1);
                break;
            case "WEEKLY":
                startDate = endDate.minusWeeks(1);
                break;
            case "MONTHLY":
                startDate = endDate.minusMonths(1);
                break;
            case "ALL_TIME":
            default:
                startDate = LocalDate.of(2020, 1, 1);
                break;
        }

        return new LocalDate[]{startDate, endDate};
    }
}
