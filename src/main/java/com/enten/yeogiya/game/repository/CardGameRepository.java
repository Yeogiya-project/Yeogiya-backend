package com.enten.yeogiya.game.repository;

import com.enten.yeogiya.game.entity.CardGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CardGameRepository extends JpaRepository<CardGame,Long> {
    List<CardGame> findByWinner(String winner);
}
