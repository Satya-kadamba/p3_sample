package com.revplay.analytics.dto;

public class ArtistDto {
    private Long id;
    private String stageName;
    private String profileImageUrl;

    public ArtistDto() {
    }

    private ArtistDto(Builder builder) {
        this.id = builder.id;
        this.stageName = builder.stageName;
        this.profileImageUrl = builder.profileImageUrl;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public static class Builder {
        private Long id;
        private String stageName;
        private String profileImageUrl;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder stageName(String stageName) {
            this.stageName = stageName;
            return this;
        }

        public Builder profileImageUrl(String profileImageUrl) {
            this.profileImageUrl = profileImageUrl;
            return this;
        }

        public ArtistDto build() {
            return new ArtistDto(this);
        }
    }
}
