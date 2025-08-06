package com.enten.yeogiya.naver_maps.service;

import com.enten.yeogiya.naver_maps.controller.response.AddressSearchResponse;
import com.enten.yeogiya.naver_maps.controller.response.AddressSearchResult;
import com.enten.yeogiya.naver_maps.controller.response.CenterPointResponse;
import com.enten.yeogiya.naver_maps.controller.response.NaverSearchApiResponse;
import com.enten.yeogiya.naver_maps.controller.response.NaverSearchItem;
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

    // 1. 주소/장소 검색 (메인 기능) - Search API만 사용
    @Override
    public Mono<AddressSearchResponse> searchPlaces(String query) {
        System.out.println("검색어: " + query);
        
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("openapi.naver.com")
                        .path("/v1/search/local.json")  
                        .queryParam("query", query)
                        .queryParam("display", 10)
                        .queryParam("start", 1)
                        .queryParam("sort", "random")
                        .build())
                .header("X-Naver-Client-Id", searchClientId)
                .header("X-Naver-Client-Secret", searchClientSecret)
                .retrieve()
                .bodyToMono(NaverSearchApiResponse.class)
                .map(naverResponse -> {
                    System.out.println("Search API 결과: " + (naverResponse.getItems() != null ? naverResponse.getItems().size() : 0) + "개");
                    
                    List<AddressSearchResult> convertedItems = new ArrayList<>();
                    if (naverResponse.getItems() != null) {
                        for (NaverSearchItem item : naverResponse.getItems()) {
                            AddressSearchResult converted = AddressSearchResult.builder()
                                    .title(item.getTitle().replaceAll("</?b>", ""))
                                    .category(item.getCategory())
                                    .address(item.getAddress())
                                    .roadAddress(item.getRoadAddress())
                                    .mapx(item.getMapx())
                                    .mapy(item.getMapy())
                                    .distance(null)
                                    .build();
                            convertedItems.add(converted);
                        }
                    }
                    
                    return AddressSearchResponse.builder()
                            .total(naverResponse.getTotal())
                            .items(convertedItems)
                            .build();
                })
                .doOnError(error -> {
                    System.err.println("Search API 호출 에러: " + error.getMessage());
                    error.printStackTrace();
                })
                .onErrorReturn(createEmptyResponse());
    }

    // 2. 중간지점 계산 (단순한 평균 계산)
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