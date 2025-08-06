package com.enten.yeogiya.game.service;

import com.enten.yeogiya.game.dto.TimingGameRequest;
import com.enten.yeogiya.game.dto.TimingGameResponse;
import com.enten.yeogiya.game.entity.TimingGame;
import com.enten.yeogiya.game.repository.TimingGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TimingGameServiceImpl implements TimingGameService {

    private final TimingGameRepository timingGameRepository;

    @Override
    public TimingGameResponse playTimingGame(TimingGameRequest request) {

        Map<String, Long> userTimings = request.getUserTimings();

        //최소 오차 계산
        long target = 3000L;
        long minDiff = userTimings.values().stream()
                .mapToLong(time -> Math.abs(time - target))
                .min()
                .orElse(Long.MAX_VALUE);

        //최소 오차 중, 가장 빠른 시간 구하기
        Long winningTime = userTimings.entrySet().stream()
                .filter(e -> Math.abs(e.getValue() - target) == minDiff)
                .map(Map.Entry::getValue)
                .min(Long::compareTo)
                .orElse(-1L);

        //해당 시간에 도달한 유저들만 승자
        List<String> winners = userTimings.entrySet().stream()
                .filter(e -> Math.abs(e.getValue() - target) == minDiff)
                .filter(e -> e.getValue().equals(winningTime))
                .map(Map.Entry::getKey)
                .toList();

        //DB 저장
        TimingGame game = TimingGame.builder()
                .userTimings(userTimings)
                .winner(String.join(",", winners))
                .playedAt(LocalDateTime.now())
                .build();

        timingGameRepository.save(game);

        return TimingGameResponse.builder()
                .winners(winners)
                .winningTime(winningTime)
                .playedAt(game.getPlayedAt())
                .build();
    }

    private String determinWinner(Map<String, Long> userTimings) {
        long target = 3000L;

        return userTimings.entrySet().stream()
                //기준 시간과 절대값 차이 계산하여 오차가 작은 사람 찾기
                .min(Comparator.comparingLong(e -> Math.abs(e.getValue() - target)))
                //승자의 userId만 반환
                .map(Map.Entry::getKey)
                //참가자가 없을 때 null 반환
                .orElse(null);
    }
}
