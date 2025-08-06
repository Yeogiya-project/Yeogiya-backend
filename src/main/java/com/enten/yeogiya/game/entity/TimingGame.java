package com.enten.yeogiya.game.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimingGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private GameType gameType = GameType.TIMING;

    @ElementCollection
    private Map<String, Long> userTimings = new HashMap<>();

    private String winner;

    private LocalDateTime playedAt = LocalDateTime.now();
}
