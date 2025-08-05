package com.enten.yeogiya.naver_maps.service;

import com.enten.yeogiya.naver_maps.controller.response.AddressSearchResponse;
import com.enten.yeogiya.naver_maps.controller.response.CenterPointResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

// 네이버 지도 API 서비스 인터페이스 - 실제 사용하는 기능만
public interface NaverMapsService {

    // 주소/장소 검색 (가장 중요한 기능)
    Mono<AddressSearchResponse> searchPlaces(String query);
    
    // 중간지점 계산
    Mono<CenterPointResponse> calculateCenterPoint(List<Map<String, Double>> locations);
}