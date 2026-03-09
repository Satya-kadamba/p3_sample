package com.revplay.artist.service;

import com.revplay.artist.dto.SongResponse;
import com.revplay.artist.dto.SongUpdateRequest;
import com.revplay.artist.dto.SongUploadRequest;
import com.revplay.artist.entity.Album;
import com.revplay.artist.entity.Artist;
import com.revplay.artist.entity.Song;
import com.revplay.artist.entity.Visibility;
import com.revplay.artist.exception.ResourceNotFoundException;
import com.revplay.artist.exception.UnauthorizedException;
import com.revplay.artist.repository.AlbumRepository;
import com.revplay.artist.repository.ArtistRepository;
import com.revplay.artist.repository.SongRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    public SongService(SongRepository songRepository,
                      ArtistRepository artistRepository,
                      AlbumRepository albumRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
    }

    @Transactional
    public SongResponse uploadSong(Long userId, SongUploadRequest request) {
        Artist artist = artistRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist profile not found. Please create an artist profile first."));

        // Validate album ownership if albumId is provided
        if (request.getAlbumId() != null) {
            albumRepository.findByArtistIdAndId(artist.getId(), request.getAlbumId())
                    .orElseThrow(() -> new ResourceNotFoundException("Album not found or does not belong to this artist"));
        }

        Song song = Song.builder()
                .artistId(artist.getId())
                .albumId(request.getAlbumId())
                .title(request.getTitle())
                .duration(request.getDuration())
                .genre(request.getGenre())
                .fileUrl(request.getFileUrl())
                .coverImageUrl(request.getCoverImageUrl())
                .visibility(request.getVisibility())
                .playCount(0L)
                .build();

        song = songRepository.save(song);
        return buildSongResponse(song, artist);
    }

    @Transactional(readOnly = true)
    public List<SongResponse> getSongsByArtist(Long userId) {
        Artist artist = artistRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist profile not found"));

        List<Song> songs = songRepository.findByArtistId(artist.getId());
        return songs.stream()
                .map(song -> buildSongResponse(song, artist))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SongResponse getSong(Long songId) {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found"));

        Artist artist = artistRepository.findById(song.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));

        return buildSongResponse(song, artist);
    }

    @Transactional
    public SongResponse updateSong(Long userId, Long songId, SongUpdateRequest request) {
        Artist artist = artistRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist profile not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found"));

        if (!song.getArtistId().equals(artist.getId())) {
            throw new UnauthorizedException("You are not authorized to update this song");
        }

        // Validate album ownership if albumId is being updated
        if (request.getAlbumId() != null) {
            albumRepository.findByArtistIdAndId(artist.getId(), request.getAlbumId())
                    .orElseThrow(() -> new ResourceNotFoundException("Album not found or does not belong to this artist"));
        }

        if (request.getTitle() != null) {
            song.setTitle(request.getTitle());
        }
        if (request.getGenre() != null) {
            song.setGenre(request.getGenre());
        }
        if (request.getCoverImageUrl() != null) {
            song.setCoverImageUrl(request.getCoverImageUrl());
        }
        if (request.getVisibility() != null) {
            song.setVisibility(request.getVisibility());
        }
        if (request.getAlbumId() != null) {
            song.setAlbumId(request.getAlbumId());
        }

        song = songRepository.save(song);
        return buildSongResponse(song, artist);
    }

    @Transactional
    public void deleteSong(Long userId, Long songId) {
        Artist artist = artistRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist profile not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found"));

        if (!song.getArtistId().equals(artist.getId())) {
            throw new UnauthorizedException("You are not authorized to delete this song");
        }

        songRepository.delete(song);
    }

    @Transactional(readOnly = true)
    public Page<SongResponse> getPublicSongs(Pageable pageable) {
        Page<Song> songs = songRepository.findByVisibility(Visibility.PUBLIC, pageable);
        return songs.map(song -> {
            Artist artist = artistRepository.findById(song.getArtistId())
                    .orElse(null);
            return buildSongResponse(song, artist);
        });
    }

    @Transactional
    public void incrementPlayCount(Long songId) {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found"));

        songRepository.incrementPlayCount(songId);
    }

    @Transactional(readOnly = true)
    public List<SongResponse> getSongsByIds(List<Long> ids) {
        List<Song> songs = songRepository.findByIdIn(ids);
        return songs.stream()
                .map(song -> {
                    Artist artist = artistRepository.findById(song.getArtistId())
                            .orElse(null);
                    return buildSongResponse(song, artist);
                })
                .collect(Collectors.toList());
    }

    private SongResponse buildSongResponse(Song song, Artist artist) {
        String artistName = artist != null ? artist.getStageName() : null;
        String albumTitle = null;

        if (song.getAlbumId() != null) {
            Album album = albumRepository.findById(song.getAlbumId()).orElse(null);
            if (album != null) {
                albumTitle = album.getTitle();
            }
        }

        return SongResponse.builder()
                .id(song.getId())
                .title(song.getTitle())
                .artistId(song.getArtistId())
                .artistName(artistName)
                .albumId(song.getAlbumId())
                .albumTitle(albumTitle)
                .duration(song.getDuration())
                .genre(song.getGenre())
                .fileUrl(song.getFileUrl())
                .coverImageUrl(song.getCoverImageUrl())
                .visibility(song.getVisibility())
                .playCount(song.getPlayCount())
                .createdAt(song.getCreatedAt())
                .build();
    }
}
