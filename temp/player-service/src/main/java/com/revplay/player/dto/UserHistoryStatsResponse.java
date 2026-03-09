package com.revplay.player.dto;

import java.util.List;

public class UserHistoryStatsResponse {

    private Long totalSongsPlayed;
    private Long totalListeningTimeSeconds;
    private Long uniqueSongsPlayed;
    private List<String> topGenres;

    public UserHistoryStatsResponse() {
    }

    private UserHistoryStatsResponse(Builder builder) {
        this.totalSongsPlayed = builder.totalSongsPlayed;
        this.totalListeningTimeSeconds = builder.totalListeningTimeSeconds;
        this.uniqueSongsPlayed = builder.uniqueSongsPlayed;
        this.topGenres = builder.topGenres;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getTotalSongsPlayed() {
        return totalSongsPlayed;
    }

    public void setTotalSongsPlayed(Long totalSongsPlayed) {
        this.totalSongsPlayed = totalSongsPlayed;
    }

    public Long getTotalListeningTimeSeconds() {
        return totalListeningTimeSeconds;
    }

    public void setTotalListeningTimeSeconds(Long totalListeningTimeSeconds) {
        this.totalListeningTimeSeconds = totalListeningTimeSeconds;
    }

    public Long getUniqueSongsPlayed() {
        return uniqueSongsPlayed;
    }

    public void setUniqueSongsPlayed(Long uniqueSongsPlayed) {
        this.uniqueSongsPlayed = uniqueSongsPlayed;
    }

    public List<String> getTopGenres() {
        return topGenres;
    }

    public void setTopGenres(List<String> topGenres) {
        this.topGenres = topGenres;
    }

    public static class Builder {
        private Long totalSongsPlayed;
        private Long totalListeningTimeSeconds;
        private Long uniqueSongsPlayed;
        private List<String> topGenres;

        public Builder totalSongsPlayed(Long totalSongsPlayed) {
            this.totalSongsPlayed = totalSongsPlayed;
            return this;
        }

        public Builder totalListeningTimeSeconds(Long totalListeningTimeSeconds) {
            this.totalListeningTimeSeconds = totalListeningTimeSeconds;
            return this;
        }

        public Builder uniqueSongsPlayed(Long uniqueSongsPlayed) {
            this.uniqueSongsPlayed = uniqueSongsPlayed;
            return this;
        }

        public Builder topGenres(List<String> topGenres) {
            this.topGenres = topGenres;
            return this;
        }

        public UserHistoryStatsResponse build() {
            return new UserHistoryStatsResponse(this);
        }
    }
}
