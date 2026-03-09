package com.revplay.artist.repository;

import com.revplay.artist.entity.Song;
import com.revplay.artist.entity.Visibility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findByArtistId(Long artistId);

    List<Song> findByArtistIdAndVisibility(Long artistId, Visibility visibility);

    Page<Song> findByVisibility(Visibility visibility, Pageable pageable);

    List<Song> findByIdIn(List<Long> ids);

    @Modifying
    @Query("UPDATE Song s SET s.playCount = s.playCount + 1 WHERE s.id = :songId")
    void incrementPlayCount(@Param("songId") Long songId);

    @Query("SELECT SUM(s.playCount) FROM Song s WHERE s.artistId = :artistId")
    Long getTotalPlaysByArtistId(@Param("artistId") Long artistId);

    long countByArtistId(Long artistId);
}
