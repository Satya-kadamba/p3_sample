package com.revplay.analytics.dto;

import java.util.List;

public class TopSongsResponse {
    private List<SongPerformanceResponse> songs;
    private String period;
    private Long artistId;

    public TopSongsResponse() {
    }

    private TopSongsResponse(Builder builder) {
        this.songs = builder.songs;
        this.period = builder.period;
        this.artistId = builder.artistId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<SongPerformanceResponse> getSongs() {
        return songs;
    }

    public void setSongs(List<SongPerformanceResponse> songs) {
        this.songs = songs;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public static class Builder {
        private List<SongPerformanceResponse> songs;
        private String period;
        private Long artistId;

        public Builder songs(List<SongPerformanceResponse> songs) {
            this.songs = songs;
            return this;
        }

        public Builder period(String period) {
            this.period = period;
            return this;
        }

        public Builder artistId(Long artistId) {
            this.artistId = artistId;
            return this;
        }

        public TopSongsResponse build() {
            return new TopSongsResponse(this);
        }
    }
}
