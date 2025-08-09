package com.enten.yeogiya.game.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimingGameResponse {
    private List<String> winners; //동점자 전부
    private long winningTime; //몇 초에 눌렀는지(ms)
    private LocalDateTime playedAt;
}
