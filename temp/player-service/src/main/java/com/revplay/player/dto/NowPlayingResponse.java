package com.revplay.player.dto;

import java.time.LocalDateTime;

public class NowPlayingResponse {

    private Long songId;
    private String title;
    private String artistName;
    private String albumTitle;
    private String coverImageUrl;
    private LocalDateTime startedAt;

    public NowPlayingResponse() {
    }

    private NowPlayingResponse(Builder builder) {
        this.songId = builder.songId;
        this.title = builder.title;
        this.artistName = builder.artistName;
        this.albumTitle = builder.albumTitle;
        this.coverImageUrl = builder.coverImageUrl;
        this.startedAt = builder.startedAt;
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

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public static class Builder {
        private Long songId;
        private String title;
        private String artistName;
        private String albumTitle;
        private String coverImageUrl;
        private LocalDateTime startedAt;

        public Builder songId(Long songId) {
            this.songId = songId;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder artistName(String artistName) {
            this.artistName = artistName;
            return this;
        }

        public Builder albumTitle(String albumTitle) {
            this.albumTitle = albumTitle;
            return this;
        }

        public Builder coverImageUrl(String coverImageUrl) {
            this.coverImageUrl = coverImageUrl;
            return this;
        }

        public Builder startedAt(LocalDateTime startedAt) {
            this.startedAt = startedAt;
            return this;
        }

        public NowPlayingResponse build() {
            return new NowPlayingResponse(this);
        }
    }
}
