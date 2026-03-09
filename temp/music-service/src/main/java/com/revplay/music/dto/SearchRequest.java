package com.revplay.music.dto;

import jakarta.validation.constraints.NotBlank;

public class SearchRequest {
    @NotBlank(message = "Query is required")
    private String query;
    private String genre;
    private String artistName;

    public SearchRequest() {
    }

    private SearchRequest(Builder builder) {
        this.query = builder.query;
        this.genre = builder.genre;
        this.artistName = builder.artistName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String query;
        private String genre;
        private String artistName;

        public Builder query(String query) {
            this.query = query;
            return this;
        }

        public Builder genre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder artistName(String artistName) {
            this.artistName = artistName;
            return this;
        }

        public SearchRequest build() {
            return new SearchRequest(this);
        }
    }

    // Getters and Setters
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
