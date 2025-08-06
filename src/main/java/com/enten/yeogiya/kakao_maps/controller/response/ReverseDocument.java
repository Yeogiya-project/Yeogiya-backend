package com.enten.yeogiya.kakao_maps.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReverseDocument {
    private ReverseAddress address;

    @JsonProperty("road_address")
    private ReverseRoadAddress placeName;
}