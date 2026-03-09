package com.revplay.user.dto;

import java.time.LocalDateTime;

public class UserProfileResponse {

    private Long id;
    private Long userId;
    private String username;
    private String bio;
    private String profileImageUrl;
    private LocalDateTime createdAt;

    public UserProfileResponse() {
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getBio() {
        return bio;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UserProfileResponse response;

        public Builder() {
            response = new UserProfileResponse();
        }

        public Builder id(Long id) {
            response.id = id;
            return this;
        }

        public Builder userId(Long userId) {
            response.userId = userId;
            return this;
        }

        public Builder username(String username) {
            response.username = username;
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

        public Builder createdAt(LocalDateTime createdAt) {
            response.createdAt = createdAt;
            return this;
        }

        public UserProfileResponse build() {
            return response;
        }
    }
}
