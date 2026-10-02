package com.Bevan.backend.service;

import com.Bevan.backend.model.Score;
import com.Bevan.backend.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    public Score createScore(Score score) {
        if (score.getPlayerName() == null
                || score.getPlayerName().isBlank()) {
            throw new IllegalArgumentException("Player name is required");
        }

        if (score.getPoint() == null) {
            throw new IllegalArgumentException("Point is required");
        }

        // Create a new record instead of updating a supplied ID.
        Score newScore = new Score(
                score.getPlayerName(),
                score.getPoint()
        );

        return scoreRepository.save(newScore);
    }

    public Optional<Score> getScoreByID(UUID scoreId) {
        return scoreRepository.findById(scoreId);
    }

    public List<Score> getAllScores(){
        return scoreRepository.findAll();
    }

    public List<Score> getRecentScores(){
        return scoreRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Score> getScoreAboveValue(Integer minValue){
        return scoreRepository.findPointGreaterThan(minValue);
    }

    public List<Score> getLeaderboard(Integer limit) {
        return scoreRepository.findTopScores(limit);
    }

    public void deleteScore(UUID scoreId) {
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new RuntimeException("Score with ID" + scoreId + " was not found "));
        scoreRepository.delete(score);


    }

}