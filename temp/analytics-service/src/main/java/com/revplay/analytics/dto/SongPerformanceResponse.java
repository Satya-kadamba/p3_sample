package com.revplay.analytics.dto;

public class SongPerformanceResponse {
    private Long songId;
    private String title;
    private Long playCount;
    private Long uniqueListeners;
    private Double averageListenDuration;
    private Double completionRate;

    public SongPerformanceResponse() {
    }

    private SongPerformanceResponse(Builder builder) {
        this.songId = builder.songId;
        this.title = builder.title;
        this.playCount = builder.playCount;
        this.uniqueListeners = builder.uniqueListeners;
        this.averageListenDuration = builder.averageListenDuration;
        this.completionRate = builder.completionRate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Long playCount) {
        this.playCount = playCount;
    }

    public Long getUniqueListeners() {
        return uniqueListeners;
    }

    public void setUniqueListeners(Long uniqueListeners) {
        this.uniqueListeners = uniqueListeners;
    }

    public Double getAverageListenDuration() {
        return averageListenDuration;
    }

    public void setAverageListenDuration(Double averageListenDuration) {
        this.averageListenDuration = averageListenDuration;
    }

    public Double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(Double completionRate) {
        this.completionRate = completionRate;
    }

    public static class Builder {
        private Long songId;
        private String title;
        private Long playCount;
        private Long uniqueListeners;
        private Double averageListenDuration;
        private Double completionRate;

        public Builder songId(Long songId) {
            this.songId = songId;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder playCount(Long playCount) {
            this.playCount = playCount;
            return this;
        }

        public Builder uniqueListeners(Long uniqueListeners) {
            this.uniqueListeners = uniqueListeners;
            return this;
        }

        public Builder averageListenDuration(Double averageListenDuration) {
            this.averageListenDuration = averageListenDuration;
            return this;
        }

        public Builder completionRate(Double completionRate) {
            this.completionRate = completionRate;
            return this;
        }

        public SongPerformanceResponse build() {
            return new SongPerformanceResponse(this);
        }
    }
}
