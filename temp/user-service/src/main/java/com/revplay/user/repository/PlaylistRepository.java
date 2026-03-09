package com.revplay.user.repository;

import com.revplay.user.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    List<Playlist> findByUserId(Long userId);

    Optional<Playlist> findByUserIdAndId(Long userId, Long id);

    Long countByUserId(Long userId);
}
