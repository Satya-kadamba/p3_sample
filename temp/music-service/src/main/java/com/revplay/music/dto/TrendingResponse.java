package com.revplay.music.dto;

import java.util.List;

public class TrendingResponse {
    private List<SongCatalogResponse> songs;
    private TrendingPeriod period;

    public TrendingResponse() {
    }

    private TrendingResponse(Builder builder) {
        this.songs = builder.songs;
        this.period = builder.period;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<SongCatalogResponse> songs;
        private TrendingPeriod period;

        public Builder songs(List<SongCatalogResponse> songs) {
            this.songs = songs;
            return this;
        }

        public Builder period(TrendingPeriod period) {
            this.period = period;
            return this;
        }

        public TrendingResponse build() {
            return new TrendingResponse(this);
        }
    }

    // Getters and Setters
    public List<SongCatalogResponse> getSongs() {
        return songs;
    }

    public void setSongs(List<SongCatalogResponse> songs) {
        this.songs = songs;
    }

    public TrendingPeriod getPeriod() {
        return period;
    }

    public void setPeriod(TrendingPeriod period) {
        this.period = period;
    }

    public enum TrendingPeriod {
        DAILY, WEEKLY, MONTHLY
    }
}
