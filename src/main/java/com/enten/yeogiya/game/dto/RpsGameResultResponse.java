package com.enten.yeogiya.game.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RpsGameResultResponse {

    private boolean draw;
    private List<String> winners;
}
