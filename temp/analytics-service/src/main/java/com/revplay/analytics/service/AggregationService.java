package com.revplay.analytics.service;

import com.revplay.analytics.client.PlayerServiceClient;
import com.revplay.analytics.dto.DailyTrendData;
import com.revplay.analytics.dto.PlayHistoryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AggregationService {

    private static final Logger logger = LoggerFactory.getLogger(AggregationService.class);

    private final PlayerServiceClient playerServiceClient;

    public AggregationService(PlayerServiceClient playerServiceClient) {
        this.playerServiceClient = playerServiceClient;
    }

    @Cacheable(value = "dailyStats", key = "#artistId + '_' + #date")
    public DailyTrendData aggregateDailyStats(Long artistId, LocalDate date) {
        logger.debug("Aggregating daily stats for artist {} on date {}", artistId, date);

        List<PlayHistoryDto> plays = playerServiceClient.getPlayTrends(
                artistId,
                date,
                date.plusDays(1)
        );

        long playCount = plays.size();
        long uniqueListeners = plays.stream()
                .map(PlayHistoryDto::getUserId)
                .distinct()
                .count();

        double totalMinutes = plays.stream()
                .mapToDouble(play -> play.getListenDurationSeconds() / 60.0)
                .sum();

        return DailyTrendData.builder()
                .date(date)
                .playCount(playCount)
                .uniqueListeners(uniqueListeners)
                .totalMinutes(Math.round(totalMinutes * 100.0) / 100.0)
                .build();
    }

    @Cacheable(value = "completionRate", key = "#songId")
    public Double calculateCompletionRate(Long songId) {
        logger.debug("Calculating completion rate for song {}", songId);

        List<PlayHistoryDto> plays = playerServiceClient.getSongPlayHistory(songId, null, null);

        if (plays.isEmpty()) {
            return 0.0;
        }

        long completedPlays = plays.stream()
                .filter(play -> Boolean.TRUE.equals(play.getCompleted()))
                .count();

        double rate = (completedPlays * 100.0) / plays.size();
        return Math.round(rate * 100.0) / 100.0;
    }

    @Cacheable(value = "uniqueListeners", key = "#artistId + '_' + #period")
    public Long calculateUniqueListeners(Long artistId, String period) {
        logger.debug("Calculating unique listeners for artist {} in period {}", artistId, period);

        LocalDate[] dateRange = getDateRangeForPeriod(period);
        List<PlayHistoryDto> plays = playerServiceClient.getPlayTrends(
                artistId,
                dateRange[0],
                dateRange[1]
        );

        return plays.stream()
                .map(PlayHistoryDto::getUserId)
                .distinct()
                .count();
    }

    public List<DailyTrendData> aggregateTrends(Long artistId, LocalDate startDate, LocalDate endDate) {
        logger.debug("Aggregating trends for artist {} from {} to {}", artistId, startDate, endDate);

        List<PlayHistoryDto> plays = playerServiceClient.getPlayTrends(artistId, startDate, endDate);

        // Group by date
        Map<LocalDate, List<PlayHistoryDto>> playsByDate = plays.stream()
                .collect(Collectors.groupingBy(play -> play.getPlayedAt().toLocalDate()));

        List<DailyTrendData> trends = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            List<PlayHistoryDto> dailyPlays = playsByDate.getOrDefault(currentDate, new ArrayList<>());

            long playCount = dailyPlays.size();
            long uniqueListeners = dailyPlays.stream()
                    .map(PlayHistoryDto::getUserId)
                    .distinct()
                    .count();

            double totalMinutes = dailyPlays.stream()
                    .mapToDouble(play -> play.getListenDurationSeconds() / 60.0)
                    .sum();

            trends.add(DailyTrendData.builder()
                    .date(currentDate)
                    .playCount(playCount)
                    .uniqueListeners(uniqueListeners)
                    .totalMinutes(Math.round(totalMinutes * 100.0) / 100.0)
                    .build());

            currentDate = currentDate.plusDays(1);
        }

        return trends;
    }

    public Long countTotalPlays(List<PlayHistoryDto> plays) {
        return (long) plays.size();
    }

    public Long countUniqueListeners(List<PlayHistoryDto> plays) {
        return plays.stream()
                .map(PlayHistoryDto::getUserId)
                .distinct()
                .count();
    }

    public Double calculateTotalListeningTime(List<PlayHistoryDto> plays) {
        double totalMinutes = plays.stream()
                .mapToDouble(play -> play.getListenDurationSeconds() / 60.0)
                .sum();
        return Math.round(totalMinutes * 100.0) / 100.0;
    }

    public Double calculateAveragePlayDuration(List<PlayHistoryDto> plays) {
        if (plays.isEmpty()) {
            return 0.0;
        }

        double avgSeconds = plays.stream()
                .mapToDouble(PlayHistoryDto::getListenDurationSeconds)
                .average()
                .orElse(0.0);

        return Math.round(avgSeconds * 100.0) / 100.0;
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
