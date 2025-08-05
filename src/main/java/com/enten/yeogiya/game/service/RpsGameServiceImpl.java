package com.enten.yeogiya.game.service;

import com.enten.yeogiya.game.dto.RpsGameRequestForm;
import com.enten.yeogiya.game.dto.RpsGameResultResponse;
import com.enten.yeogiya.game.entity.RpsChoice;
import com.enten.yeogiya.game.entity.RpsGame;
import com.enten.yeogiya.game.repository.RpsGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RpsGameServiceImpl implements RpsGameService {

    private final RpsGameRepository rpsGameRepository;

    @Override
    public RpsGameResultResponse play(List<RpsGameRequestForm> requestForms) {
        Map<String, RpsChoice> choices = requestForms.stream()
                .collect(Collectors.toMap(RpsGameRequestForm::getName, RpsGameRequestForm::getChoice));

        Set<RpsChoice> distinctChoices = new HashSet<>(choices.values());

        boolean isDraw = distinctChoices.size() != 2;
        List<String> winners;

        if(isDraw){
            winners = List.of();
        } else{
            RpsChoice winChoice = determineWinner(distinctChoices);
            winners = choices.entrySet().stream()
                    .filter(e -> e.getValue() == winChoice)
                    .map(Map.Entry::getKey)
                    .toList();
        }

        //저장
        RpsGame game = new RpsGame();
        game.setUserChoices(choices);
        game.setWinner(String.join(",", winners));
        rpsGameRepository.save(game);

        return new RpsGameResultResponse(isDraw, winners);
    }

    private RpsChoice determineWinner(Set<RpsChoice> choices){
        if(choices.contains(RpsChoice.ROCK) && choices.contains(RpsChoice.SCISSORS)) return RpsChoice.ROCK;
        if(choices.contains(RpsChoice.SCISSORS) && choices.contains(RpsChoice.PAPER)) return RpsChoice.SCISSORS;
        if(choices.contains(RpsChoice.PAPER) && choices.contains(RpsChoice.ROCK)) return RpsChoice.PAPER;
        throw new IllegalArgumentException("Invalid RPS combination: " + choices);
    }
}
