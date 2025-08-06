package com.enten.yeogiya.kakao_maps.service;

import com.enten.yeogiya.kakao_maps.controller.request.MeetingPlaceRequest;
import com.enten.yeogiya.kakao_maps.controller.request.ReverseGeocodingRequest;
import com.enten.yeogiya.kakao_maps.controller.request.SearchRequest;
import com.enten.yeogiya.kakao_maps.controller.request.SearchType;
import com.enten.yeogiya.kakao_maps.controller.response.MeetingCenterPoint;
import com.enten.yeogiya.kakao_maps.controller.response.MeetingPlaceResponse;
import com.enten.yeogiya.kakao_maps.controller.response.ReverseGeocodingResponse;
import com.enten.yeogiya.kakao_maps.controller.response.SearchResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface KakaoMapsService {
    Mono<SearchResponse> searchPlaces(SearchRequest query, SearchType searchType);

    Mono<ReverseGeocodingResponse> reverseGeocoding(ReverseGeocodingRequest query);

    Mono<MeetingCenterPoint> calculateCenterPoint(List<Map<String, Double>> locations)
            ;
    Mono<MeetingPlaceResponse> findMeetingPlace(MeetingPlaceRequest request);
}
