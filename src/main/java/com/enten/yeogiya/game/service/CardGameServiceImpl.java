package com.enten.yeogiya.game.service;

import com.enten.yeogiya.game.entity.CardGame;
import com.enten.yeogiya.game.repository.CardGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardGameServiceImpl implements CardGameService {

    private final CardGameRepository cardGameRepository;

    @Override
    public CardGame playCardGame(List<String> participants) {
        Map<String, Integer> cards = new HashMap<>();
        Random random = new Random();

        for(String userId : participants){
            int card = random.nextInt(13) + 1;
            cards.put(userId, card);
        }

        String winner = cards.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        CardGame result = new CardGame();
        result.getUserCards().putAll(cards);
        result.setWinner(winner);

        return cardGameRepository.save(result);
    }
}
