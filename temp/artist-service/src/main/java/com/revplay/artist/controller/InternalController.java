package com.revplay.artist.controller;

import com.revplay.artist.dto.SongResponse;
import com.revplay.artist.service.SongService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/internal")
public class InternalController {

    private final SongService songService;

    public InternalController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/songs/{songId}")
    public ResponseEntity<SongResponse> getSongById(@PathVariable Long songId) {
        SongResponse response = songService.getSong(songId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/songs/batch")
    public ResponseEntity<List<SongResponse>> getSongsByIds(@RequestBody List<Long> ids) {
        List<SongResponse> songs = songService.getSongsByIds(ids);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/songs/public")
    public ResponseEntity<Page<SongResponse>> getPublicSongs(Pageable pageable) {
        Page<SongResponse> songs = songService.getPublicSongs(pageable);
        return ResponseEntity.ok(songs);
    }

    @PostMapping("/songs/{songId}/play")
    public ResponseEntity<Void> incrementPlayCount(@PathVariable Long songId) {
        songService.incrementPlayCount(songId);
        return ResponseEntity.ok().build();
    }
}
