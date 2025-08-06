package com.enten.yeogiya.kakao_maps.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MeetingPlaceRequest {
    private List<MeetingLocation> meetingLocations;
    private CategoryGroupCode categoryGroupCode;
    private Integer radius;
    private Integer size;


}