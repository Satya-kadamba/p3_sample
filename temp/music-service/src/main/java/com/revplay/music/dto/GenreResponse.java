package com.revplay.music.dto;

public class GenreResponse {
    private String name;
    private Long songCount;

    public GenreResponse() {
    }

    private GenreResponse(Builder builder) {
        this.name = builder.name;
        this.songCount = builder.songCount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private Long songCount;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder songCount(Long songCount) {
            this.songCount = songCount;
            return this;
        }

        public GenreResponse build() {
            return new GenreResponse(this);
        }
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSongCount() {
        return songCount;
    }

    public void setSongCount(Long songCount) {
        this.songCount = songCount;
    }
}
