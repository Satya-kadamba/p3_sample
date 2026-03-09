package com.revplay.user.dto;

import java.time.LocalDateTime;

public class FavoriteResponse {

    private Long id;
    private Long songId;
    private String songTitle;
    private String artistName;
    private LocalDateTime addedAt;

    public FavoriteResponse() {
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getSongId() {
        return songId;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public String getArtistName() {
        return artistName;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private FavoriteResponse response;

        public Builder() {
            response = new FavoriteResponse();
        }

        public Builder id(Long id) {
            response.id = id;
            return this;
        }

        public Builder songId(Long songId) {
            response.songId = songId;
            return this;
        }

        public Builder songTitle(String songTitle) {
            response.songTitle = songTitle;
            return this;
        }

        public Builder artistName(String artistName) {
            response.artistName = artistName;
            return this;
        }

        public Builder addedAt(LocalDateTime addedAt) {
            response.addedAt = addedAt;
            return this;
        }

        public FavoriteResponse build() {
            return response;
        }
    }
}
