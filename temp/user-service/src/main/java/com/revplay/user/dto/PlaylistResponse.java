package com.revplay.user.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PlaylistResponse {

    private Long id;
    private String name;
    private String description;
    private Boolean isPublic;
    private Integer songCount;
    private List<SongDto> songs;
    private LocalDateTime createdAt;

    public PlaylistResponse() {
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public Integer getSongCount() {
        return songCount;
    }

    public List<SongDto> getSongs() {
        return songs;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public void setSongCount(Integer songCount) {
        this.songCount = songCount;
    }

    public void setSongs(List<SongDto> songs) {
        this.songs = songs;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private PlaylistResponse response;

        public Builder() {
            response = new PlaylistResponse();
        }

        public Builder id(Long id) {
            response.id = id;
            return this;
        }

        public Builder name(String name) {
            response.name = name;
            return this;
        }

        public Builder description(String description) {
            response.description = description;
            return this;
        }

        public Builder isPublic(Boolean isPublic) {
            response.isPublic = isPublic;
            return this;
        }

        public Builder songCount(Integer songCount) {
            response.songCount = songCount;
            return this;
        }

        public Builder songs(List<SongDto> songs) {
            response.songs = songs;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            response.createdAt = createdAt;
            return this;
        }

        public PlaylistResponse build() {
            return response;
        }
    }
}
