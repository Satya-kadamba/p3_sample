package com.revplay.user.dto;

public class UserStatsResponse {

    private Long playlistCount;
    private Long favoriteCount;
    private Long totalListeningTime;

    public UserStatsResponse() {
    }

    // Getters
    public Long getPlaylistCount() {
        return playlistCount;
    }

    public Long getFavoriteCount() {
        return favoriteCount;
    }

    public Long getTotalListeningTime() {
        return totalListeningTime;
    }

    // Setters
    public void setPlaylistCount(Long playlistCount) {
        this.playlistCount = playlistCount;
    }

    public void setFavoriteCount(Long favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public void setTotalListeningTime(Long totalListeningTime) {
        this.totalListeningTime = totalListeningTime;
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UserStatsResponse response;

        public Builder() {
            response = new UserStatsResponse();
        }

        public Builder playlistCount(Long playlistCount) {
            response.playlistCount = playlistCount;
            return this;
        }

        public Builder favoriteCount(Long favoriteCount) {
            response.favoriteCount = favoriteCount;
            return this;
        }

        public Builder totalListeningTime(Long totalListeningTime) {
            response.totalListeningTime = totalListeningTime;
            return this;
        }

        public UserStatsResponse build() {
            return response;
        }
    }
}
