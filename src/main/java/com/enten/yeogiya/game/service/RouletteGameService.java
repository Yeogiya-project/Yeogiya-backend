package com.enten.yeogiya.game.service;

import com.enten.yeogiya.game.entity.RouletteGame;

import java.util.List;

public interface RouletteGameService {
    RouletteGame playRouletteGame(List<String> userIds);
}
