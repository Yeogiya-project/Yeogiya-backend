package com.enten.yeogiya.game.entity;

public enum RpsChoice {
    ROCK, PAPER, SCISSORS;

    //가위<바위<보<가위
    public boolean wins(RpsChoice other) {
        return (this == ROCK && other == SCISSORS) ||
                (this == PAPER && other == ROCK) ||
                (this == SCISSORS && other == PAPER);
    }
}