package com.revplay.user.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "playlist_songs")
public class PlaylistSong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long playlistId;

    @Column(nullable = false)
    private Long songId;

    @Column(nullable = false)
    private Integer position;

    @Column(nullable = false, updatable = false)
    private LocalDateTime addedAt;

    public PlaylistSong() {
    }

    @PrePersist
    protected void onCreate() {
        addedAt = LocalDateTime.now();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getPlaylistId() {
        return playlistId;
    }

    public Long getSongId() {
        return songId;
    }

    public Integer getPosition() {
        return position;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setPlaylistId(Long playlistId) {
        this.playlistId = playlistId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private PlaylistSong playlistSong;

        public Builder() {
            playlistSong = new PlaylistSong();
        }

        public Builder id(Long id) {
            playlistSong.id = id;
            return this;
        }

        public Builder playlistId(Long playlistId) {
            playlistSong.playlistId = playlistId;
            return this;
        }

        public Builder songId(Long songId) {
            playlistSong.songId = songId;
            return this;
        }

        public Builder position(Integer position) {
            playlistSong.position = position;
            return this;
        }

        public Builder addedAt(LocalDateTime addedAt) {
            playlistSong.addedAt = addedAt;
            return this;
        }

        public PlaylistSong build() {
            return playlistSong;
        }
    }
}
