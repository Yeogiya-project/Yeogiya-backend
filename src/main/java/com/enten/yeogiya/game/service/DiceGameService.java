package com.enten.yeogiya.game.service;

import com.enten.yeogiya.game.entity.DiceGame;

import java.util.List;

public interface DiceGameService {
    DiceGame playDiceGame(List<String> participants);
}
