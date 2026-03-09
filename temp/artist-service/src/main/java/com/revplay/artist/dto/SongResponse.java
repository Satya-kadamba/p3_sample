package com.revplay.artist.dto;

import com.revplay.artist.entity.Visibility;
import java.time.LocalDateTime;

public class SongResponse {

    private Long id;
    private String title;
    private Long artistId;
    private String artistName;
    private Long albumId;
    private String albumTitle;
    private Integer duration;
    private String genre;
    private String fileUrl;
    private String coverImageUrl;
    private Visibility visibility;
    private Long playCount;
    private LocalDateTime createdAt;

    // Constructors
    public SongResponse() {
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

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
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

    public Long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Long playCount) {
        this.playCount = playCount;
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
        private SongResponse response = new SongResponse();

        public Builder id(Long id) {
            response.id = id;
            return this;
        }

        public Builder title(String title) {
            response.title = title;
            return this;
        }

        public Builder artistId(Long artistId) {
            response.artistId = artistId;
            return this;
        }

        public Builder artistName(String artistName) {
            response.artistName = artistName;
            return this;
        }

        public Builder albumId(Long albumId) {
            response.albumId = albumId;
            return this;
        }

        public Builder albumTitle(String albumTitle) {
            response.albumTitle = albumTitle;
            return this;
        }

        public Builder duration(Integer duration) {
            response.duration = duration;
            return this;
        }

        public Builder genre(String genre) {
            response.genre = genre;
            return this;
        }

        public Builder fileUrl(String fileUrl) {
            response.fileUrl = fileUrl;
            return this;
        }

        public Builder coverImageUrl(String coverImageUrl) {
            response.coverImageUrl = coverImageUrl;
            return this;
        }

        public Builder visibility(Visibility visibility) {
            response.visibility = visibility;
            return this;
        }

        public Builder playCount(Long playCount) {
            response.playCount = playCount;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            response.createdAt = createdAt;
            return this;
        }

        public SongResponse build() {
            return response;
        }
    }
}
