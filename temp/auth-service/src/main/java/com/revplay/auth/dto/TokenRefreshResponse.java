package com.revplay.auth.dto;

public class TokenRefreshResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType;

    // Constructors
    public TokenRefreshResponse() {
        this.tokenType = "Bearer";
    }

    public TokenRefreshResponse(String accessToken, String refreshToken, String tokenType) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
    }

    // Getters and Setters
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    // Builder Pattern
    public static class Builder {
        private String accessToken;
        private String refreshToken;
        private String tokenType = "Bearer";

        public Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public Builder tokenType(String tokenType) {
            this.tokenType = tokenType;
            return this;
        }

        public TokenRefreshResponse build() {
            return new TokenRefreshResponse(accessToken, refreshToken, tokenType);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
