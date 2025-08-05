package com.enten.yeogiya.kakao_maps.service;

import com.enten.yeogiya.kakao_maps.controller.request.ReverseGeocodingRequest;
import com.enten.yeogiya.kakao_maps.controller.request.SearchRequest;
import com.enten.yeogiya.kakao_maps.controller.response.ReverseGeocodingResponse;
import com.enten.yeogiya.kakao_maps.controller.response.SearchResponse;
import com.enten.yeogiya.kakao_maps.controller.request.SearchType;
import reactor.core.publisher.Mono;

public interface KakaoMapsService {
    Mono<SearchResponse> searchPlaces(SearchRequest query, SearchType searchType);

    Mono<ReverseGeocodingResponse> reverseGeocoding(ReverseGeocodingRequest query);
}
