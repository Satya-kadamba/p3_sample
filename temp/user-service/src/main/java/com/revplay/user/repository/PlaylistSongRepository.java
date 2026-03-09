package com.revplay.user.repository;

import com.revplay.user.entity.PlaylistSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistSongRepository extends JpaRepository<PlaylistSong, Long> {

    List<PlaylistSong> findByPlaylistIdOrderByPosition(Long playlistId);

    void deleteByPlaylistIdAndSongId(Long playlistId, Long songId);

    void deleteByPlaylistId(Long playlistId);

    @Query("SELECT COALESCE(MAX(ps.position), 0) FROM PlaylistSong ps WHERE ps.playlistId = :playlistId")
    Integer findMaxPositionByPlaylistId(Long playlistId);

    Long countByPlaylistId(Long playlistId);
}
