package com.enten.yeogiya.game.service;

import com.enten.yeogiya.game.dto.RpsGameRequestForm;
import com.enten.yeogiya.game.dto.RpsGameResultResponse;

import java.util.List;

public interface RpsGameService {
    RpsGameResultResponse play(List<RpsGameRequestForm> requestForms);
}
