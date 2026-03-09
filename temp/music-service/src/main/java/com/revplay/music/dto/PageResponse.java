package com.revplay.music.dto;

import java.util.List;

public class PageResponse<T> {
    private List<T> content;
    private Integer page;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;

    public PageResponse() {
    }

    private PageResponse(Builder<T> builder) {
        this.content = builder.content;
        this.page = builder.page;
        this.pageSize = builder.pageSize;
        this.totalElements = builder.totalElements;
        this.totalPages = builder.totalPages;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> {
        private List<T> content;
        private Integer page;
        private Integer pageSize;
        private Long totalElements;
        private Integer totalPages;

        public Builder<T> content(List<T> content) {
            this.content = content;
            return this;
        }

        public Builder<T> page(Integer page) {
            this.page = page;
            return this;
        }

        public Builder<T> pageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder<T> totalElements(Long totalElements) {
            this.totalElements = totalElements;
            return this;
        }

        public Builder<T> totalPages(Integer totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public PageResponse<T> build() {
            return new PageResponse<>(this);
        }
    }

    // Getters and Setters
    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
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

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
