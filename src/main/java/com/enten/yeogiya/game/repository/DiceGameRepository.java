package com.enten.yeogiya.game.repository;

import com.enten.yeogiya.game.entity.DiceGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiceGameRepository extends JpaRepository<DiceGame, Long> {
    List<DiceGame> findByWinner(String winner);
}
