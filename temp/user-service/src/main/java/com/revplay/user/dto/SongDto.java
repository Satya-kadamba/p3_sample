package com.revplay.user.dto;

public class SongDto {

    private Long id;
    private String title;
    private String artistName;
    private String albumName;
    private Integer duration;
    private String coverImageUrl;

    public SongDto() {
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private SongDto dto;

        public Builder() {
            dto = new SongDto();
        }

        public Builder id(Long id) {
            dto.id = id;
            return this;
        }

        public Builder title(String title) {
            dto.title = title;
            return this;
        }

        public Builder artistName(String artistName) {
            dto.artistName = artistName;
            return this;
        }

        public Builder albumName(String albumName) {
            dto.albumName = albumName;
            return this;
        }

        public Builder duration(Integer duration) {
            dto.duration = duration;
            return this;
        }

        public Builder coverImageUrl(String coverImageUrl) {
            dto.coverImageUrl = coverImageUrl;
            return this;
        }

        public SongDto build() {
            return dto;
        }
    }
}
