package com.revplay.music.controller;

import com.revplay.music.dto.SearchRequest;
import com.revplay.music.dto.SearchResponse;
import com.revplay.music.service.SearchService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/catalog")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public ResponseEntity<SearchResponse> searchSongs(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        SearchResponse results = searchService.searchSongs(q, page, size);
        return ResponseEntity.ok(results);
    }

    @PostMapping("/search/advanced")
    public ResponseEntity<SearchResponse> advancedSearch(
            @Valid @RequestBody SearchRequest searchRequest,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        SearchResponse results = searchService.advancedSearch(searchRequest, page, size);
        return ResponseEntity.ok(results);
    }
}
