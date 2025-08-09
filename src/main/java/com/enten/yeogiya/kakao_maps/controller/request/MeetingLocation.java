package com.enten.yeogiya.kakao_maps.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MeetingLocation {
    private Double lat;
    private Double lng;
}