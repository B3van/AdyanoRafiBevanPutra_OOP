package com.Bevan.backend.repository;

import com.Bevan.backend.model.Score;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ScoreRepository extends JpaRepository<Score, UUID> {

    @Query("SELECT s FROM Score s WHERE s.point > :minValue")
    List<Score> findPointGreaterThan(@Param("minValue") Integer minValue);

    List<Score> findAllByOrderByCreatedAtDesc();

    @Query("SELECT s FROM Score s ORDER BY s.point DESC")
    List<Score> findTopScores(Pageable pageable);

    default List<Score> findTopScores(Integer limit) {
        if (limit == null || limit <= 0) {
            throw new IllegalArgumentException("Limit must be greater than zero");
        }

        return findTopScores(PageRequest.of(0, limit));
    }
}