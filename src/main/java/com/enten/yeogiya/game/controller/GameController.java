package com.enten.yeogiya.game.controller;

import com.enten.yeogiya.game.entity.CardGame;
import com.enten.yeogiya.game.entity.DiceGame;
import com.enten.yeogiya.game.service.CardGameService;
import com.enten.yeogiya.game.service.DiceGameService;
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

    @PostMapping("/dice/play")
    public ResponseEntity<DiceGame> play(@RequestBody List<String> participants) {
        return ResponseEntity.ok(diceGameService.playDiceGame(participants));
    }

    @PostMapping("card/play")
    public ResponseEntity<CardGame> playCard(@RequestBody List<String> participants) {
        return ResponseEntity.ok(cardGameService.playCardGame(participants));
    }
}
