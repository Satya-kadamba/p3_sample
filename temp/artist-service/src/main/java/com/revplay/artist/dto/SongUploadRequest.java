package com.revplay.artist.dto;

import com.revplay.artist.entity.Visibility;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SongUploadRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
    private String title;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 second")
    private Integer duration;

    @NotBlank(message = "Genre is required")
    private String genre;

    @NotBlank(message = "File URL is required")
    private String fileUrl;

    private String coverImageUrl;

    @NotNull(message = "Visibility is required")
    private Visibility visibility;

    private Long albumId;

    // Constructors
    public SongUploadRequest() {
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
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
        private SongUploadRequest request = new SongUploadRequest();

        public Builder title(String title) {
            request.title = title;
            return this;
        }

        public Builder duration(Integer duration) {
            request.duration = duration;
            return this;
        }

        public Builder genre(String genre) {
            request.genre = genre;
            return this;
        }

        public Builder fileUrl(String fileUrl) {
            request.fileUrl = fileUrl;
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

        public SongUploadRequest build() {
            return request;
        }
    }
}
