package com.revplay.analytics.dto;

public class AnalyticsOverviewResponse {
    private Long totalPlays;
    private Long uniqueListeners;
    private Double totalListeningTimeMinutes;
    private Double averagePlayDuration;
    private Long topSongId;
    private String topSongTitle;
    private String period;

    public AnalyticsOverviewResponse() {
    }

    private AnalyticsOverviewResponse(Builder builder) {
        this.totalPlays = builder.totalPlays;
        this.uniqueListeners = builder.uniqueListeners;
        this.totalListeningTimeMinutes = builder.totalListeningTimeMinutes;
        this.averagePlayDuration = builder.averagePlayDuration;
        this.topSongId = builder.topSongId;
        this.topSongTitle = builder.topSongTitle;
        this.period = builder.period;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getTotalPlays() {
        return totalPlays;
    }

    public void setTotalPlays(Long totalPlays) {
        this.totalPlays = totalPlays;
    }

    public Long getUniqueListeners() {
        return uniqueListeners;
    }

    public void setUniqueListeners(Long uniqueListeners) {
        this.uniqueListeners = uniqueListeners;
    }

    public Double getTotalListeningTimeMinutes() {
        return totalListeningTimeMinutes;
    }

    public void setTotalListeningTimeMinutes(Double totalListeningTimeMinutes) {
        this.totalListeningTimeMinutes = totalListeningTimeMinutes;
    }

    public Double getAveragePlayDuration() {
        return averagePlayDuration;
    }

    public void setAveragePlayDuration(Double averagePlayDuration) {
        this.averagePlayDuration = averagePlayDuration;
    }

    public Long getTopSongId() {
        return topSongId;
    }

    public void setTopSongId(Long topSongId) {
        this.topSongId = topSongId;
    }

    public String getTopSongTitle() {
        return topSongTitle;
    }

    public void setTopSongTitle(String topSongTitle) {
        this.topSongTitle = topSongTitle;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public static class Builder {
        private Long totalPlays;
        private Long uniqueListeners;
        private Double totalListeningTimeMinutes;
        private Double averagePlayDuration;
        private Long topSongId;
        private String topSongTitle;
        private String period;

        public Builder totalPlays(Long totalPlays) {
            this.totalPlays = totalPlays;
            return this;
        }

        public Builder uniqueListeners(Long uniqueListeners) {
            this.uniqueListeners = uniqueListeners;
            return this;
        }

        public Builder totalListeningTimeMinutes(Double totalListeningTimeMinutes) {
            this.totalListeningTimeMinutes = totalListeningTimeMinutes;
            return this;
        }

        public Builder averagePlayDuration(Double averagePlayDuration) {
            this.averagePlayDuration = averagePlayDuration;
            return this;
        }

        public Builder topSongId(Long topSongId) {
            this.topSongId = topSongId;
            return this;
        }

        public Builder topSongTitle(String topSongTitle) {
            this.topSongTitle = topSongTitle;
            return this;
        }

        public Builder period(String period) {
            this.period = period;
            return this;
        }

        public AnalyticsOverviewResponse build() {
            return new AnalyticsOverviewResponse(this);
        }
    }
}
