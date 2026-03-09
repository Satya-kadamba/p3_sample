package com.revplay.player.dto;

import java.time.LocalDateTime;

public class ListeningHistoryResponse {

    private Long id;
    private Long songId;
    private String songTitle;
    private String artistName;
    private String albumTitle;
    private String coverImageUrl;
    private LocalDateTime playedAt;
    private Integer listenedDuration;

    public ListeningHistoryResponse() {
    }

    private ListeningHistoryResponse(Builder builder) {
        this.id = builder.id;
        this.songId = builder.songId;
        this.songTitle = builder.songTitle;
        this.artistName = builder.artistName;
        this.albumTitle = builder.albumTitle;
        this.coverImageUrl = builder.coverImageUrl;
        this.playedAt = builder.playedAt;
        this.listenedDuration = builder.listenedDuration;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
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

    public LocalDateTime getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(LocalDateTime playedAt) {
        this.playedAt = playedAt;
    }

    public Integer getListenedDuration() {
        return listenedDuration;
    }

    public void setListenedDuration(Integer listenedDuration) {
        this.listenedDuration = listenedDuration;
    }

    public static class Builder {
        private Long id;
        private Long songId;
        private String songTitle;
        private String artistName;
        private String albumTitle;
        private String coverImageUrl;
        private LocalDateTime playedAt;
        private Integer listenedDuration;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder songId(Long songId) {
            this.songId = songId;
            return this;
        }

        public Builder songTitle(String songTitle) {
            this.songTitle = songTitle;
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

        public Builder playedAt(LocalDateTime playedAt) {
            this.playedAt = playedAt;
            return this;
        }

        public Builder listenedDuration(Integer listenedDuration) {
            this.listenedDuration = listenedDuration;
            return this;
        }

        public ListeningHistoryResponse build() {
            return new ListeningHistoryResponse(this);
        }
    }
}
