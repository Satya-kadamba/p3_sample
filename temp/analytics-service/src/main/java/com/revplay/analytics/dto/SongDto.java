package com.revplay.analytics.dto;

public class SongDto {
    private Long id;
    private String title;
    private Long artistId;
    private String albumTitle;
    private String coverImageUrl;

    public SongDto() {
    }

    private SongDto(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.artistId = builder.artistId;
        this.albumTitle = builder.albumTitle;
        this.coverImageUrl = builder.coverImageUrl;
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

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public static class Builder {
        private Long id;
        private String title;
        private Long artistId;
        private String albumTitle;
        private String coverImageUrl;

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

        public Builder albumTitle(String albumTitle) {
            this.albumTitle = albumTitle;
            return this;
        }

        public Builder coverImageUrl(String coverImageUrl) {
            this.coverImageUrl = coverImageUrl;
            return this;
        }

        public SongDto build() {
            return new SongDto(this);
        }
    }
}
