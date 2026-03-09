package com.revplay.user.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "favorites", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"userId", "songId"})
})
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long songId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime addedAt;

    public Favorite() {
    }

    @PrePersist
    protected void onCreate() {
        addedAt = LocalDateTime.now();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getSongId() {
        return songId;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Favorite favorite;

        public Builder() {
            favorite = new Favorite();
        }

        public Builder id(Long id) {
            favorite.id = id;
            return this;
        }

        public Builder userId(Long userId) {
            favorite.userId = userId;
            return this;
        }

        public Builder songId(Long songId) {
            favorite.songId = songId;
            return this;
        }

        public Builder addedAt(LocalDateTime addedAt) {
            favorite.addedAt = addedAt;
            return this;
        }

        public Favorite build() {
            return favorite;
        }
    }
}
