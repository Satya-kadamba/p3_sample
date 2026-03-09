package com.revplay.player.dto;

import jakarta.validation.constraints.NotNull;

public class PlayRequest {

    @NotNull(message = "Song ID is required")
    private Long songId;

    public PlayRequest() {
    }

    private PlayRequest(Builder builder) {
        this.songId = builder.songId;
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

    public static class Builder {
        private Long songId;

        public Builder songId(Long songId) {
            this.songId = songId;
            return this;
        }

        public PlayRequest build() {
            return new PlayRequest(this);
        }
    }
}
