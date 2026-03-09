package com.revplay.music.service;

import com.revplay.music.client.ArtistServiceClient;
import com.revplay.music.dto.GenreResponse;
import com.revplay.music.dto.PageResponse;
import com.revplay.music.dto.SongCatalogResponse;
import com.revplay.music.exception.ServiceUnavailableException;
import feign.FeignException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CatalogService {

    private final ArtistServiceClient artistServiceClient;

    public CatalogService(ArtistServiceClient artistServiceClient) {
        this.artistServiceClient = artistServiceClient;
    }

    @Cacheable(value = "songs", key = "#page + '-' + #size")
    public PageResponse<SongCatalogResponse> getPublicSongs(int page, int size) {
        try {
            return artistServiceClient.getPublicSongs(page, size);
        } catch (FeignException e) {
            throw new ServiceUnavailableException("Artist service is currently unavailable", e);
        }
    }

    @Cacheable(value = "songs", key = "#songId")
    public SongCatalogResponse getSongById(Long songId) {
        try {
            return artistServiceClient.getSongById(songId);
        } catch (FeignException.NotFound e) {
            throw new ServiceUnavailableException("Song not found with id: " + songId);
        } catch (FeignException e) {
            throw new ServiceUnavailableException("Artist service is currently unavailable", e);
        }
    }

    public PageResponse<SongCatalogResponse> getSongsByGenre(String genre, int page, int size) {
        try {
            // Fetch all public songs and filter by genre
            PageResponse<SongCatalogResponse> allSongs = artistServiceClient.getPublicSongs(page, size);

            List<SongCatalogResponse> filteredSongs = allSongs.getContent().stream()
                    .filter(song -> song.getGenre() != null &&
                            song.getGenre().equalsIgnoreCase(genre))
                    .collect(Collectors.toList());

            return PageResponse.<SongCatalogResponse>builder()
                    .content(filteredSongs)
                    .page(page)
                    .pageSize(size)
                    .totalElements((long) filteredSongs.size())
                    .totalPages((int) Math.ceil((double) filteredSongs.size() / size))
                    .build();
        } catch (FeignException e) {
            throw new ServiceUnavailableException("Artist service is currently unavailable", e);
        }
    }

    @Cacheable(value = "genres")
    public List<GenreResponse> getGenres() {
        try {
            // Fetch all songs and extract unique genres
            PageResponse<SongCatalogResponse> allSongs = artistServiceClient.getPublicSongs(0, 1000);

            Map<String, Long> genreCounts = allSongs.getContent().stream()
                    .filter(song -> song.getGenre() != null && !song.getGenre().isEmpty())
                    .collect(Collectors.groupingBy(
                            SongCatalogResponse::getGenre,
                            Collectors.counting()
                    ));

            List<GenreResponse> genres = new ArrayList<>();
            genreCounts.forEach((genre, count) -> {
                genres.add(GenreResponse.builder()
                        .name(genre)
                        .songCount(count)
                        .build());
            });

            return genres;
        } catch (FeignException e) {
            throw new ServiceUnavailableException("Artist service is currently unavailable", e);
        }
    }
}
