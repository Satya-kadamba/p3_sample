package com.revplay.music.dto;

import java.util.List;

public class SearchResponse {
    private List<SongCatalogResponse> songs;
    private Long totalResults;
    private Integer page;
    private Integer pageSize;

    public SearchResponse() {
    }

    private SearchResponse(Builder builder) {
        this.songs = builder.songs;
        this.totalResults = builder.totalResults;
        this.page = builder.page;
        this.pageSize = builder.pageSize;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<SongCatalogResponse> songs;
        private Long totalResults;
        private Integer page;
        private Integer pageSize;

        public Builder songs(List<SongCatalogResponse> songs) {
            this.songs = songs;
            return this;
        }

        public Builder totalResults(Long totalResults) {
            this.totalResults = totalResults;
            return this;
        }

        public Builder page(Integer page) {
            this.page = page;
            return this;
        }

        public Builder pageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public SearchResponse build() {
            return new SearchResponse(this);
        }
    }

    // Getters and Setters
    public List<SongCatalogResponse> getSongs() {
        return songs;
    }

    public void setSongs(List<SongCatalogResponse> songs) {
        this.songs = songs;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
