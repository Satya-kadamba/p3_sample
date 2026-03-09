package com.revplay.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserProfileRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @Size(max = 500, message = "Bio must not exceed 500 characters")
    private String bio;

    @Size(max = 500, message = "Profile image URL must not exceed 500 characters")
    private String profileImageUrl;

    public UserProfileRequest() {
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getBio() {
        return bio;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UserProfileRequest request;

        public Builder() {
            request = new UserProfileRequest();
        }

        public Builder username(String username) {
            request.username = username;
            return this;
        }

        public Builder bio(String bio) {
            request.bio = bio;
            return this;
        }

        public Builder profileImageUrl(String profileImageUrl) {
            request.profileImageUrl = profileImageUrl;
            return this;
        }

        public UserProfileRequest build() {
            return request;
        }
    }
}
