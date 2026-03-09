package com.revplay.user.dto;

import jakarta.validation.constraints.NotNull;

public class FavoriteRequest {

    @NotNull(message = "Song ID is required")
    private Long songId;

    public FavoriteRequest() {
    }

    // Getters
    public Long getSongId() {
        return songId;
    }

    // Setters
    public void setSongId(Long songId) {
        this.songId = songId;
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private FavoriteRequest request;

        public Builder() {
            request = new FavoriteRequest();
        }

        public Builder songId(Long songId) {
            request.songId = songId;
            return this;
        }

        public FavoriteRequest build() {
            return request;
        }
    }
}
