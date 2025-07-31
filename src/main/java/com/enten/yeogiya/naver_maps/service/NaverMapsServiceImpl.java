package com.enten.yeogiya.naver_maps.service;

import com.enten.yeogiya.naver_maps.controller.response.AddressSearchResponse;
import com.enten.yeogiya.naver_maps.controller.response.AddressSearchResult;
import com.enten.yeogiya.naver_maps.controller.response.CenterPointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor  
public class NaverMapsServiceImpl implements NaverMapsService {

    @Value("${naver.search.client-id}")
    private String searchClientId;

    @Value("${naver.search.client-secret}")
    private String searchClientSecret;

    private final WebClient webClient;

    // 1. 주소/장소 검색 (메인 기능)
    @Override
    public Mono<AddressSearchResponse> searchPlaces(String query) {
        System.out.println("검색어: " + query);
        
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("openapi.naver.com")
                        .path("/v1/search/local.json")
                        .queryParam("query", query)
                        .queryParam("display", 10)   // 10개 결과
                        .queryParam("start", 1)
                        .queryParam("sort", "random") // 랜덤 정렬
                        .build())
                .header("X-Naver-Client-Id", searchClientId)
                .header("X-Naver-Client-Secret", searchClientSecret)
                .retrieve()
                .bodyToMono(AddressSearchResponse.class)
                .map(response -> {
                    System.out.println("검색 결과: " + (response.getItems() != null ? response.getItems().size() : 0) + "개");
                    return response;
                })
                .onErrorReturn(createEmptyResponse());
    }

    // 2. 근처 지하철역 검색
    @Override
    public Mono<AddressSearchResponse> findNearbyStations(double lat, double lng) {
        System.out.println("근처 지하철역 검색: " + lat + ", " + lng);
        
        // 좌표 기반으로 해당 지역의 지하철역 검색
        String areaQuery = getAreaFromCoordinates(lat, lng) + " 지하철역";
        
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("openapi.naver.com")
                        .path("/v1/search/local.json")
                        .queryParam("query", areaQuery)
                        .queryParam("display", 10)
                        .queryParam("start", 1)
                        .queryParam("sort", "comment") // 리뷰 많은 순
                        .build())
                .header("X-Naver-Client-Id", searchClientId)
                .header("X-Naver-Client-Secret", searchClientSecret)
                .retrieve()
                .bodyToMono(AddressSearchResponse.class)
                .map(response -> {
                    // 지하철역만 필터링
                    List<AddressSearchResult> stations = response.getItems().stream()
                            .filter(item -> item.getTitle().contains("역") || 
                                          item.getCategory().contains("지하철"))
                            .limit(5) // 최대 5개만
                            .toList();
                    
                    System.out.println("찾은 지하철역: " + stations.size() + "개");
                    return AddressSearchResponse.builder()
                            .total(stations.size())
                            .items(stations)
                            .build();
                })
                .onErrorReturn(createEmptyResponse());
    }
    
    // 좌표로 대략적인 지역명 추정
    private String getAreaFromCoordinates(double lat, double lng) {
        // 서울 지역 좌표 범위로 간단히 구분
        if (lat >= 37.4 && lat <= 37.7 && lng >= 126.8 && lng <= 127.2) {
            if (lng >= 127.0) {
                return lat >= 37.55 ? "강북구" : "강남구";
            } else {
                return lat >= 37.55 ? "마포구" : "강남구";
            }
        }
        return "서울"; // 기본값
    }

    // 3. 중간지점 계산 (단순한 평균 계산)
    @Override
    public Mono<CenterPointResponse> calculateCenterPoint(List<Map<String, Double>> locations) {
        return Mono.fromCallable(() -> {
            if (locations.isEmpty()) {
                throw new IllegalArgumentException("위치 정보가 없습니다.");
            }
            
            System.out.println("중간지점 계산: " + locations.size() + "개 위치");
            
            // 모든 위치의 평균 계산
            double totalLat = 0;
            double totalLng = 0;
            
            for (Map<String, Double> location : locations) {
                totalLat += location.get("lat");
                totalLng += location.get("lng");
            }
            
            double centerLat = totalLat / locations.size();
            double centerLng = totalLng / locations.size();
            
            System.out.println("계산된 중간지점: " + centerLat + ", " + centerLng);
            
            return CenterPointResponse.builder()
                    .lat(centerLat)
                    .lng(centerLng)
                    .count(locations.size())
                    .build();
        });
    }
    
    // 빈 응답 생성 헬퍼 메서드
    private AddressSearchResponse createEmptyResponse() {
        return AddressSearchResponse.builder()
                .total(0)
                .items(new ArrayList<>())
                .build();
    }
}