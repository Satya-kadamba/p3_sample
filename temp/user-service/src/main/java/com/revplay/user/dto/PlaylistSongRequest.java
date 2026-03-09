package com.revplay.user.dto;

import jakarta.validation.constraints.NotNull;

public class PlaylistSongRequest {

    @NotNull(message = "Song ID is required")
    private Long songId;

    private Integer position;

    public PlaylistSongRequest() {
    }

    // Getters
    public Long getSongId() {
        return songId;
    }

    public Integer getPosition() {
        return position;
    }

    // Setters
    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private PlaylistSongRequest request;

        public Builder() {
            request = new PlaylistSongRequest();
        }

        public Builder songId(Long songId) {
            request.songId = songId;
            return this;
        }

        public Builder position(Integer position) {
            request.position = position;
            return this;
        }

        public PlaylistSongRequest build() {
            return request;
        }
    }
}
