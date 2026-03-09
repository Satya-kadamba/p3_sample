package com.revplay.artist.repository;

import com.revplay.artist.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Optional<Artist> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
