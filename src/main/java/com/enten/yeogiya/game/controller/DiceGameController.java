package com.enten.yeogiya.game.controller;

import com.enten.yeogiya.game.entity.DiceGame;
import com.enten.yeogiya.game.service.DiceGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/games/dice")
@RequiredArgsConstructor
public class DiceGameController {

    private final DiceGameService diceGameService;

    @PostMapping("play")
    public ResponseEntity<DiceGame> play(@RequestBody List<String> participants) {
        DiceGame result = diceGameService.playDiceGame(participants);
        return ResponseEntity.ok(result);
    }
}
