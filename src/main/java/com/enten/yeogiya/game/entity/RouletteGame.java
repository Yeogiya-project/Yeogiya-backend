package com.enten.yeogiya.game.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
public class RouletteGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //userId : 룰렛 결과 숫자
    @ElementCollection
    private Map<String, Integer> userSpins = new HashMap<>();

    //가장 높은 숫자를 뽑은 승자들
    @ElementCollection
    private List<String> winners = new ArrayList<>();

    private LocalDateTime playedAt = LocalDateTime.now();
}
