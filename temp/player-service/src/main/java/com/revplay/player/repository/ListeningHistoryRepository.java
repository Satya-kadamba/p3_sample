package com.revplay.player.repository;

import com.revplay.player.entity.ListeningHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ListeningHistoryRepository extends JpaRepository<ListeningHistory, Long> {

    Page<ListeningHistory> findByUserIdOrderByPlayedAtDesc(Long userId, Pageable pageable);

    List<ListeningHistory> findByUserId(Long userId);

    void deleteByUserId(Long userId);

    long countByUserId(Long userId);

    @Query("SELECT DISTINCT lh.songId FROM ListeningHistory lh WHERE lh.userId = :userId")
    List<Long> findDistinctSongIdsByUserId(@Param("userId") Long userId);

    @Query("SELECT SUM(lh.duration) FROM ListeningHistory lh WHERE lh.userId = :userId")
    Long sumDurationByUserId(@Param("userId") Long userId);

    List<ListeningHistory> findByUserIdAndPlayedAtBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    List<ListeningHistory> findBySongId(Long songId);

    @Query("SELECT lh FROM ListeningHistory lh WHERE lh.songId IN :songIds")
    List<ListeningHistory> findBySongIdIn(@Param("songIds") List<Long> songIds);

    @Query("SELECT lh.songId, COUNT(lh) as playCount FROM ListeningHistory lh WHERE lh.userId = :userId GROUP BY lh.songId ORDER BY playCount DESC")
    List<Object[]> findTopSongsByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT DATE(lh.playedAt) as playDate, COUNT(lh) as playCount FROM ListeningHistory lh WHERE lh.playedAt BETWEEN :startDate AND :endDate GROUP BY DATE(lh.playedAt) ORDER BY playDate")
    List<Object[]> findDailyPlayCounts(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT lh.songId, COUNT(DISTINCT lh.userId) as listenerCount FROM ListeningHistory lh WHERE lh.songId IN :songIds GROUP BY lh.songId")
    List<Object[]> findListenerCountsBySongIds(@Param("songIds") List<Long> songIds);
}
