package com.revplay.analytics.dto;

import java.util.List;

public class ListeningTrendResponse {
    private List<DailyTrendData> trends;

    public ListeningTrendResponse() {
    }

    private ListeningTrendResponse(Builder builder) {
        this.trends = builder.trends;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<DailyTrendData> getTrends() {
        return trends;
    }

    public void setTrends(List<DailyTrendData> trends) {
        this.trends = trends;
    }

    public static class Builder {
        private List<DailyTrendData> trends;

        public Builder trends(List<DailyTrendData> trends) {
            this.trends = trends;
            return this;
        }

        public ListeningTrendResponse build() {
            return new ListeningTrendResponse(this);
        }
    }
}
