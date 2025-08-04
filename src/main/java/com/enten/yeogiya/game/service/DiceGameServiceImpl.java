package com.enten.yeogiya.game.service;

import com.enten.yeogiya.game.entity.DiceGame;
import com.enten.yeogiya.game.repository.DiceGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DiceGameServiceImpl implements DiceGameService {

    private final DiceGameRepository diceGameRepository;

    @Override
    public DiceGame playDiceGame(List<String> participants) {
        Map<String, Integer> results = new HashMap<>();
        Random random = new Random();

        for(String userId : participants){
            int dice = random.nextInt(6) + 1;
            results.put(userId, dice);
        }

        String winner = results.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        DiceGame game = new DiceGame();
        game.getUserResults().putAll(results);
        game.setWinner(winner);

        return diceGameRepository.save(game);
    }
}
