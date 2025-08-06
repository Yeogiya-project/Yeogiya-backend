package com.enten.yeogiya.kakao_maps.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReverseGeocodingRequest {
    private String x;
    private String y;
    private String inputCoord;
}
