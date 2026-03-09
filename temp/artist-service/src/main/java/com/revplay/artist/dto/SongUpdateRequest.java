package com.revplay.artist.dto;

import com.revplay.artist.entity.Visibility;
import jakarta.validation.constraints.Size;

public class SongUpdateRequest {

    @Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
    private String title;

    private String genre;

    private String coverImageUrl;

    private Visibility visibility;

    private Long albumId;

    // Constructors
    public SongUpdateRequest() {
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private SongUpdateRequest request = new SongUpdateRequest();

        public Builder title(String title) {
            request.title = title;
            return this;
        }

        public Builder genre(String genre) {
            request.genre = genre;
            return this;
        }

        public Builder coverImageUrl(String coverImageUrl) {
            request.coverImageUrl = coverImageUrl;
            return this;
        }

        public Builder visibility(Visibility visibility) {
            request.visibility = visibility;
            return this;
        }

        public Builder albumId(Long albumId) {
            request.albumId = albumId;
            return this;
        }

        public SongUpdateRequest build() {
            return request;
        }
    }
}
