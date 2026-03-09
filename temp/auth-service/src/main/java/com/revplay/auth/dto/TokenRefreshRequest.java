package com.revplay.auth.dto;

import jakarta.validation.constraints.NotBlank;

public class TokenRefreshRequest {

    @NotBlank(message = "Refresh token is required")
    private String refreshToken;

    // Constructors
    public TokenRefreshRequest() {
    }

    public TokenRefreshRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    // Getters and Setters
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    // Builder Pattern
    public static class Builder {
        private String refreshToken;

        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public TokenRefreshRequest build() {
            return new TokenRefreshRequest(refreshToken);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
