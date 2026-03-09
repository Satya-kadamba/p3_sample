package com.revplay.analytics.dto;

public class UserDto {
    private Long id;
    private String username;
    private String profileImageUrl;

    public UserDto() {
    }

    private UserDto(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public static class Builder {
        private Long id;
        private String username;
        private String profileImageUrl;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder profileImageUrl(String profileImageUrl) {
            this.profileImageUrl = profileImageUrl;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }
}
