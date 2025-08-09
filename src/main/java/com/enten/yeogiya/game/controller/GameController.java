package com.enten.yeogiya.game.controller;

import com.enten.yeogiya.game.dto.RpsGameRequestForm;
import com.enten.yeogiya.game.dto.RpsGameResultResponse;
import com.enten.yeogiya.game.dto.TimingGameRequest;
import com.enten.yeogiya.game.dto.TimingGameResponse;
import com.enten.yeogiya.game.entity.CardGame;
import com.enten.yeogiya.game.entity.DiceGame;
import com.enten.yeogiya.game.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private final DiceGameService diceGameService;
    private final CardGameService cardGameService;
    private final RpsGameService rpsGameService;
    private final TimingGameService timingGameService;

    @PostMapping("/dice/play")
    public ResponseEntity<DiceGame> play(@RequestBody List<String> participants) {
        return ResponseEntity.ok(diceGameService.playDiceGame(participants));
    }

    @PostMapping("/card/play")
    public ResponseEntity<CardGame> playCard(@RequestBody List<String> participants) {
        return ResponseEntity.ok(cardGameService.playCardGame(participants));
    }

    @PostMapping("/rps/play")
    public RpsGameResultResponse playRps(@RequestBody List<RpsGameRequestForm> requestForms) {
        return rpsGameService.play(requestForms);
    }

    @PostMapping("/timing/play")
    public ResponseEntity<TimingGameResponse> playTimingGame(@RequestBody TimingGameRequest request) {
        TimingGameResponse response = timingGameService.playTimingGame(request);
        return ResponseEntity.ok(response);
    }
}
