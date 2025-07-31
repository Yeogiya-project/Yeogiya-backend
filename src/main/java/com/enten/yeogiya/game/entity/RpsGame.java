package com.enten.yeogiya.game.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
public class RpsGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private GameType gameType = GameType.RPS;

    @ElementCollection
    private Map<String, RpsChoice> userChoices = new HashMap<>();

    private String winner;

    private LocalDateTime playedAt = LocalDateTime.now();
}
