package com.enten.yeogiya.naver_maps.controller;

import com.enten.yeogiya.naver_maps.controller.response.AddressSearchResponse;
import com.enten.yeogiya.naver_maps.controller.response.CenterPointResponse;
import com.enten.yeogiya.naver_maps.service.NaverMapsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

// 네이버 지도 API 컨트롤러 - 실제 사용하는 기능만
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/naver-maps")
public class NaverMapsController {
    
    private final NaverMapsService naverMapsService;
    
    // 1. 주소/장소 검색 API (가장 중요한 기능)
    // 사용법: GET /api/naver-maps/search?query=강남역
    @GetMapping("/search")
    public Mono<ResponseEntity<AddressSearchResponse>> searchPlaces(@RequestParam String query) {
        System.out.println("검색 요청: " + query);
        
        return naverMapsService.searchPlaces(query)
                .map(result -> {
                    System.out.println("검색 완료: " + result.getTotal() + "개 결과");
                    return ResponseEntity.ok(result);
                })
                .onErrorResume(error -> {
                    System.err.println("검색 에러: " + error.getMessage());
                    return Mono.just(ResponseEntity.internalServerError().build());
                });
    }
    
    // 2. 근처 지하철역 검색 API
    // 사용법: GET /api/naver-maps/nearby-stations?lat=37.5666&lng=126.9784
    @GetMapping("/nearby-stations")
    public Mono<ResponseEntity<AddressSearchResponse>> findNearbyStations(
            @RequestParam double lat, 
            @RequestParam double lng) {
        System.out.println("근처 지하철역 검색 요청: " + lat + ", " + lng);
        
        return naverMapsService.findNearbyStations(lat, lng)
                .map(result -> {
                    System.out.println("지하철역 검색 완료: " + result.getTotal() + "개 발견");
                    return ResponseEntity.ok(result);
                })
                .onErrorResume(error -> {
                    System.err.println("지하철역 검색 에러: " + error.getMessage());
                    return Mono.just(ResponseEntity.internalServerError().build());
                });
    }
    
    // 3. 중간지점 계산 API
    // 사용법: POST /api/naver-maps/center-point
    // Body: [{"lat": 37.5666, "lng": 126.9784}, {"lat": 37.5567, "lng": 126.9723}]
    @PostMapping("/center-point")
    public Mono<ResponseEntity<CenterPointResponse>> calculateCenterPoint(
            @RequestBody List<Map<String, Double>> locations) {
        System.out.println("중간지점 계산 요청: " + locations.size() + "개 위치");
        
        return naverMapsService.calculateCenterPoint(locations)
                .map(result -> {
                    System.out.println("중간지점 계산 완료");
                    return ResponseEntity.ok(result);
                })
                .onErrorResume(error -> {
                    System.err.println("중간지점 계산 에러: " + error.getMessage());
                    return Mono.just(ResponseEntity.internalServerError().build());
                });
    }
}