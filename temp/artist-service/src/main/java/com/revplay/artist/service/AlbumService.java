package com.revplay.artist.service;

import com.revplay.artist.dto.AlbumRequest;
import com.revplay.artist.dto.AlbumResponse;
import com.revplay.artist.dto.SongResponse;
import com.revplay.artist.entity.Album;
import com.revplay.artist.entity.Artist;
import com.revplay.artist.entity.Song;
import com.revplay.artist.exception.ResourceNotFoundException;
import com.revplay.artist.exception.UnauthorizedException;
import com.revplay.artist.repository.AlbumRepository;
import com.revplay.artist.repository.ArtistRepository;
import com.revplay.artist.repository.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;

    public AlbumService(AlbumRepository albumRepository,
                       ArtistRepository artistRepository,
                       SongRepository songRepository) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
    }

    @Transactional
    public AlbumResponse createAlbum(Long userId, AlbumRequest request) {
        Artist artist = artistRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist profile not found. Please create an artist profile first."));

        Album album = Album.builder()
                .artistId(artist.getId())
                .title(request.getTitle())
                .description(request.getDescription())
                .coverImageUrl(request.getCoverImageUrl())
                .releaseDate(request.getReleaseDate())
                .build();

        album = albumRepository.save(album);
        return buildAlbumResponse(album, artist);
    }

    @Transactional(readOnly = true)
    public List<AlbumResponse> getAlbumsByArtist(Long userId) {
        Artist artist = artistRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist profile not found"));

        List<Album> albums = albumRepository.findByArtistId(artist.getId());
        return albums.stream()
                .map(album -> buildAlbumResponse(album, artist))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AlbumResponse getAlbum(Long albumId) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("Album not found"));

        Artist artist = artistRepository.findById(album.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));

        return buildAlbumResponse(album, artist);
    }

    @Transactional
    public AlbumResponse updateAlbum(Long userId, Long albumId, AlbumRequest request) {
        Artist artist = artistRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist profile not found"));

        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("Album not found"));

        if (!album.getArtistId().equals(artist.getId())) {
            throw new UnauthorizedException("You are not authorized to update this album");
        }

        if (request.getTitle() != null) {
            album.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            album.setDescription(request.getDescription());
        }
        if (request.getCoverImageUrl() != null) {
            album.setCoverImageUrl(request.getCoverImageUrl());
        }
        if (request.getReleaseDate() != null) {
            album.setReleaseDate(request.getReleaseDate());
        }

        album = albumRepository.save(album);
        return buildAlbumResponse(album, artist);
    }

    @Transactional
    public void deleteAlbum(Long userId, Long albumId) {
        Artist artist = artistRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist profile not found"));

        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("Album not found"));

        if (!album.getArtistId().equals(artist.getId())) {
            throw new UnauthorizedException("You are not authorized to delete this album");
        }

        // Remove album reference from songs
        List<Song> songs = songRepository.findByArtistId(artist.getId());
        for (Song song : songs) {
            if (albumId.equals(song.getAlbumId())) {
                song.setAlbumId(null);
                songRepository.save(song);
            }
        }

        albumRepository.delete(album);
    }

    private AlbumResponse buildAlbumResponse(Album album, Artist artist) {
        List<Song> songs = songRepository.findByArtistId(artist.getId())
                .stream()
                .filter(song -> album.getId().equals(song.getAlbumId()))
                .collect(Collectors.toList());

        List<SongResponse> songResponses = songs.stream()
                .map(song -> SongResponse.builder()
                        .id(song.getId())
                        .title(song.getTitle())
                        .artistId(song.getArtistId())
                        .artistName(artist.getStageName())
                        .albumId(song.getAlbumId())
                        .albumTitle(album.getTitle())
                        .duration(song.getDuration())
                        .genre(song.getGenre())
                        .fileUrl(song.getFileUrl())
                        .coverImageUrl(song.getCoverImageUrl())
                        .visibility(song.getVisibility())
                        .playCount(song.getPlayCount())
                        .createdAt(song.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        return AlbumResponse.builder()
                .id(album.getId())
                .title(album.getTitle())
                .description(album.getDescription())
                .coverImageUrl(album.getCoverImageUrl())
                .releaseDate(album.getReleaseDate())
                .songCount((long) songResponses.size())
                .songs(songResponses)
                .createdAt(album.getCreatedAt())
                .build();
    }
}
