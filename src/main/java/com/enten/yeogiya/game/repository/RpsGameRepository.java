package com.enten.yeogiya.game.repository;

import com.enten.yeogiya.game.entity.RpsGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RpsGameRepository extends JpaRepository<RpsGame, Long> {
}
