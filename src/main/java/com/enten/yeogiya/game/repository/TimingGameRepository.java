package com.enten.yeogiya.game.repository;

import com.enten.yeogiya.game.entity.TimingGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimingGameRepository extends JpaRepository<TimingGame,Long> {
}
