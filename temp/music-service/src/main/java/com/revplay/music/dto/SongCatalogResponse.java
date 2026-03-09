package com.revplay.music.dto;

public class SongCatalogResponse {
    private Long id;
    private String title;
    private Long artistId;
    private String artistName;
    private Long albumId;
    private String albumTitle;
    private Integer duration;
    private String genre;
    private String coverImageUrl;
    private Long playCount;

    public SongCatalogResponse() {
    }

    private SongCatalogResponse(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.artistId = builder.artistId;
        this.artistName = builder.artistName;
        this.albumId = builder.albumId;
        this.albumTitle = builder.albumTitle;
        this.duration = builder.duration;
        this.genre = builder.genre;
        this.coverImageUrl = builder.coverImageUrl;
        this.playCount = builder.playCount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String title;
        private Long artistId;
        private String artistName;
        private Long albumId;
        private String albumTitle;
        private Integer duration;
        private String genre;
        private String coverImageUrl;
        private Long playCount;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder artistId(Long artistId) {
            this.artistId = artistId;
            return this;
        }

        public Builder artistName(String artistName) {
            this.artistName = artistName;
            return this;
        }

        public Builder albumId(Long albumId) {
            this.albumId = albumId;
            return this;
        }

        public Builder albumTitle(String albumTitle) {
            this.albumTitle = albumTitle;
            return this;
        }

        public Builder duration(Integer duration) {
            this.duration = duration;
            return this;
        }

        public Builder genre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder coverImageUrl(String coverImageUrl) {
            this.coverImageUrl = coverImageUrl;
            return this;
        }

        public Builder playCount(Long playCount) {
            this.playCount = playCount;
            return this;
        }

        public SongCatalogResponse build() {
            return new SongCatalogResponse(this);
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public Long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Long playCount) {
        this.playCount = playCount;
    }
}
