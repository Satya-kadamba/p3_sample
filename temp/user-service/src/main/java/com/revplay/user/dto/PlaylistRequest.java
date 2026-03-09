package com.revplay.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PlaylistRequest {

    @NotBlank(message = "Playlist name is required")
    @Size(max = 100, message = "Playlist name must not exceed 100 characters")
    private String name;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "isPublic field is required")
    private Boolean isPublic;

    public PlaylistRequest() {
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private PlaylistRequest request;

        public Builder() {
            request = new PlaylistRequest();
        }

        public Builder name(String name) {
            request.name = name;
            return this;
        }

        public Builder description(String description) {
            request.description = description;
            return this;
        }

        public Builder isPublic(Boolean isPublic) {
            request.isPublic = isPublic;
            return this;
        }

        public PlaylistRequest build() {
            return request;
        }
    }
}
