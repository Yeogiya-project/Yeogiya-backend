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
public class CardGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private GameType gameType = GameType.CARD;

    @ElementCollection
    private Map<String, Integer> userCards = new HashMap<>(); // userId : 카드 숫자 (1~13)

    private String winner;

    private LocalDateTime playedAt = LocalDateTime.now();
}
