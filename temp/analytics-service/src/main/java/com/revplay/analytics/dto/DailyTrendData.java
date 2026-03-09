package com.revplay.analytics.dto;

import java.time.LocalDate;

public class DailyTrendData {
    private LocalDate date;
    private Long playCount;
    private Long uniqueListeners;
    private Double totalMinutes;

    public DailyTrendData() {
    }

    private DailyTrendData(Builder builder) {
        this.date = builder.date;
        this.playCount = builder.playCount;
        this.uniqueListeners = builder.uniqueListeners;
        this.totalMinutes = builder.totalMinutes;
    }

    public static Builder builder() {
        return new Builder();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Long playCount) {
        this.playCount = playCount;
    }

    public Long getUniqueListeners() {
        return uniqueListeners;
    }

    public void setUniqueListeners(Long uniqueListeners) {
        this.uniqueListeners = uniqueListeners;
    }

    public Double getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(Double totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public static class Builder {
        private LocalDate date;
        private Long playCount;
        private Long uniqueListeners;
        private Double totalMinutes;

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder playCount(Long playCount) {
            this.playCount = playCount;
            return this;
        }

        public Builder uniqueListeners(Long uniqueListeners) {
            this.uniqueListeners = uniqueListeners;
            return this;
        }

        public Builder totalMinutes(Double totalMinutes) {
            this.totalMinutes = totalMinutes;
            return this;
        }

        public DailyTrendData build() {
            return new DailyTrendData(this);
        }
    }
}
