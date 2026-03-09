package com.revplay.analytics.dto;

import java.time.LocalDateTime;

public class PlayHistoryDto {
    private Long id;
    private Long userId;
    private Long songId;
    private LocalDateTime playedAt;
    private Integer listenDurationSeconds;
    private Boolean completed;

    public PlayHistoryDto() {
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

    public Integer getListenDurationSeconds() {
        return listenDurationSeconds;
    }

    public void setListenDurationSeconds(Integer listenDurationSeconds) {
        this.listenDurationSeconds = listenDurationSeconds;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
