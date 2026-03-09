package com.revplay.analytics.dto;

public class ListenerStatsDto {
    private Long userId;
    private Long playCount;
    private Double totalListeningMinutes;

    public ListenerStatsDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Long playCount) {
        this.playCount = playCount;
    }

    public Double getTotalListeningMinutes() {
        return totalListeningMinutes;
    }

    public void setTotalListeningMinutes(Double totalListeningMinutes) {
        this.totalListeningMinutes = totalListeningMinutes;
    }
}
