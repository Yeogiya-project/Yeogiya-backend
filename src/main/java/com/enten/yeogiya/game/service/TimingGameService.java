package com.enten.yeogiya.game.service;

import com.enten.yeogiya.game.dto.TimingGameRequest;
import com.enten.yeogiya.game.dto.TimingGameResponse;

public interface TimingGameService {
    TimingGameResponse playTimingGame(TimingGameRequest request);
}
