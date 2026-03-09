package com.revplay.artist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class AlbumRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
    private String title;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    private String coverImageUrl;

    @NotNull(message = "Release date is required")
    private LocalDate releaseDate;

    // Constructors
    public AlbumRequest() {
    }

    // Getters and Setters
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

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private AlbumRequest request = new AlbumRequest();

        public Builder title(String title) {
            request.title = title;
            return this;
        }

        public Builder description(String description) {
            request.description = description;
            return this;
        }

        public Builder coverImageUrl(String coverImageUrl) {
            request.coverImageUrl = coverImageUrl;
            return this;
        }

        public Builder releaseDate(LocalDate releaseDate) {
            request.releaseDate = releaseDate;
            return this;
        }

        public AlbumRequest build() {
            return request;
        }
    }
}
