package com.revplay.artist.dto;

import java.time.LocalDateTime;

public class ArtistProfileResponse {

    private Long id;
    private Long userId;
    private String stageName;
    private String bio;
    private String profileImageUrl;
    private Boolean verified;
    private Long songCount;
    private Long albumCount;
    private Long totalPlays;
    private LocalDateTime createdAt;

    // Constructors
    public ArtistProfileResponse() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Long getSongCount() {
        return songCount;
    }

    public void setSongCount(Long songCount) {
        this.songCount = songCount;
    }

    public Long getAlbumCount() {
        return albumCount;
    }

    public void setAlbumCount(Long albumCount) {
        this.albumCount = albumCount;
    }

    public Long getTotalPlays() {
        return totalPlays;
    }

    public void setTotalPlays(Long totalPlays) {
        this.totalPlays = totalPlays;
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
        private ArtistProfileResponse response = new ArtistProfileResponse();

        public Builder id(Long id) {
            response.id = id;
            return this;
        }

        public Builder userId(Long userId) {
            response.userId = userId;
            return this;
        }

        public Builder stageName(String stageName) {
            response.stageName = stageName;
            return this;
        }

        public Builder bio(String bio) {
            response.bio = bio;
            return this;
        }

        public Builder profileImageUrl(String profileImageUrl) {
            response.profileImageUrl = profileImageUrl;
            return this;
        }

        public Builder verified(Boolean verified) {
            response.verified = verified;
            return this;
        }

        public Builder songCount(Long songCount) {
            response.songCount = songCount;
            return this;
        }

        public Builder albumCount(Long albumCount) {
            response.albumCount = albumCount;
            return this;
        }

        public Builder totalPlays(Long totalPlays) {
            response.totalPlays = totalPlays;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            response.createdAt = createdAt;
            return this;
        }

        public ArtistProfileResponse build() {
            return response;
        }
    }
}
