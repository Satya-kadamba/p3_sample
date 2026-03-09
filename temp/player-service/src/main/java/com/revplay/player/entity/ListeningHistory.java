package com.revplay.player.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "listening_history")
public class ListeningHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long songId;

    @Column(nullable = false)
    private LocalDateTime playedAt;

    @Column(nullable = false)
    private Integer duration;

    public ListeningHistory() {
    }

    private ListeningHistory(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.songId = builder.songId;
        this.playedAt = builder.playedAt;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public LocalDateTime getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(LocalDateTime playedAt) {
        this.playedAt = playedAt;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public static class Builder {
        private Long id;
        private Long userId;
        private Long songId;
        private LocalDateTime playedAt;
        private Integer duration;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder songId(Long songId) {
            this.songId = songId;
            return this;
        }

        public Builder playedAt(LocalDateTime playedAt) {
            this.playedAt = playedAt;
            return this;
        }

        public Builder duration(Integer duration) {
            this.duration = duration;
            return this;
        }

        public ListeningHistory build() {
            return new ListeningHistory(this);
        }
    }
}
