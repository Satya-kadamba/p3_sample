package com.revplay.player.dto;

public class SongDto {

    private Long id;
    private String title;
    private String artistName;
    private String albumTitle;
    private String fileUrl;
    private String coverImageUrl;
    private Integer duration;

    public SongDto() {
    }

    private SongDto(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.artistName = builder.artistName;
        this.albumTitle = builder.albumTitle;
        this.fileUrl = builder.fileUrl;
        this.coverImageUrl = builder.coverImageUrl;
        this.duration = builder.duration;
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

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public static class Builder {
        private Long id;
        private String title;
        private String artistName;
        private String albumTitle;
        private String fileUrl;
        private String coverImageUrl;
        private Integer duration;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder artistName(String artistName) {
            this.artistName = artistName;
            return this;
        }

        public Builder albumTitle(String albumTitle) {
            this.albumTitle = albumTitle;
            return this;
        }

        public Builder fileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
            return this;
        }

        public Builder coverImageUrl(String coverImageUrl) {
            this.coverImageUrl = coverImageUrl;
            return this;
        }

        public Builder duration(Integer duration) {
            this.duration = duration;
            return this;
        }

        public SongDto build() {
            return new SongDto(this);
        }
    }
}
