package com.revplay.analytics.dto;

public class TopListenerResponse {
    private Long userId;
    private String username;
    private Long playCount;
    private Double totalListeningMinutes;

    public TopListenerResponse() {
    }

    private TopListenerResponse(Builder builder) {
        this.userId = builder.userId;
        this.username = builder.username;
        this.playCount = builder.playCount;
        this.totalListeningMinutes = builder.totalListeningMinutes;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Long playCount) {
        this.playCount = playCount;
    }

    public Double getTotalListeningMinutes() {
        return totalListeningMinutes;
    }

    public void setTotalListeningMinutes(Double totalListeningMinutes) {
        this.totalListeningMinutes = totalListeningMinutes;
    }

    public static class Builder {
        private Long userId;
        private String username;
        private Long playCount;
        private Double totalListeningMinutes;

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder playCount(Long playCount) {
            this.playCount = playCount;
            return this;
        }

        public Builder totalListeningMinutes(Double totalListeningMinutes) {
            this.totalListeningMinutes = totalListeningMinutes;
            return this;
        }

        public TopListenerResponse build() {
            return new TopListenerResponse(this);
        }
    }
}
