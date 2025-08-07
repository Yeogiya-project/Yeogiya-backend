package com.enten.yeogiya.game.service;

import com.enten.yeogiya.game.entity.RouletteGame;
import com.enten.yeogiya.game.repository.RouletteGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RouletteGameServiceImpl implements RouletteGameService {

    private final RouletteGameRepository rouletteGameRepository;

    @Override
    public RouletteGame playRouletteGame(List<String> userIds) {
        Map<String, Integer> spins = new HashMap<>();
        int max = Integer.MIN_VALUE;

        // 룰렛 돌리기: 랜덤 숫자 부여 (1~100)
        for (String userId : userIds) {
            int spin = (int)(Math.random() * 100) + 1;
            spins.put(userId, spin);
            max = Math.max(max, spin);
        }

        final int finalMax = max;

        // 최고 숫자를 뽑은 사람들 추출
        List<String> winners = spins.entrySet().stream()
                .filter(entry -> entry.getValue() == finalMax)
                .map(Map.Entry::getKey)
                .toList();

        // 결과 객체 생성 및 저장
        RouletteGame game = new RouletteGame();
        game.setUserSpins(spins);
        game.setWinners(winners);

        return rouletteGameRepository.save(game);
    }
}
