package com.enten.yeogiya.game.service;

import com.enten.yeogiya.game.entity.CardGame;

import java.util.List;

public interface CardGameService {
    CardGame playCardGame(List<String> participants);
}
