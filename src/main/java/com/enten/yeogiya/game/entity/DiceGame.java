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
public class DiceGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private GameType gameType = GameType.DICE;

    @ElementCollection
    private Map<String, Integer> userResults = new HashMap<>(); //userId : 주사위 값

    private String winner;

    private LocalDateTime playedAt = LocalDateTime.now();
}
