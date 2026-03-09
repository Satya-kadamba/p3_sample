package com.revplay.artist.repository;

import com.revplay.artist.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findByArtistId(Long artistId);

    Optional<Album> findByArtistIdAndId(Long artistId, Long id);

    long countByArtistId(Long artistId);
}
