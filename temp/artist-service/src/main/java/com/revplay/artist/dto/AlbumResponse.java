package com.revplay.artist.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AlbumResponse {

    private Long id;
    private String title;
    private String description;
    private String coverImageUrl;
    private LocalDate releaseDate;
    private Long songCount;
    private List<SongResponse> songs;
    private LocalDateTime createdAt;

    // Constructors
    public AlbumResponse() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Long getSongCount() {
        return songCount;
    }

    public void setSongCount(Long songCount) {
        this.songCount = songCount;
    }

    public List<SongResponse> getSongs() {
        return songs;
    }

    public void setSongs(List<SongResponse> songs) {
        this.songs = songs;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private AlbumResponse response = new AlbumResponse();

        public Builder id(Long id) {
            response.id = id;
            return this;
        }

        public Builder title(String title) {
            response.title = title;
            return this;
        }

        public Builder description(String description) {
            response.description = description;
            return this;
        }

        public Builder coverImageUrl(String coverImageUrl) {
            response.coverImageUrl = coverImageUrl;
            return this;
        }

        public Builder releaseDate(LocalDate releaseDate) {
            response.releaseDate = releaseDate;
            return this;
        }

        public Builder songCount(Long songCount) {
            response.songCount = songCount;
            return this;
        }

        public Builder songs(List<SongResponse> songs) {
            response.songs = songs;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            response.createdAt = createdAt;
            return this;
        }

        public AlbumResponse build() {
            return response;
        }
    }
}
