package com.enten.yeogiya.game.dto;

import com.enten.yeogiya.game.entity.RpsChoice;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RpsGameRequestForm {

    private String name;
    private RpsChoice choice;
}
