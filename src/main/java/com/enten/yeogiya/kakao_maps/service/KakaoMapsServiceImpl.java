package com.enten.yeogiya.kakao_maps.service;

import com.enten.yeogiya.kakao_maps.controller.request.*;
import com.enten.yeogiya.kakao_maps.controller.response.MeetingCenterPoint;
import com.enten.yeogiya.kakao_maps.controller.response.MeetingPlaceResponse;
import com.enten.yeogiya.kakao_maps.controller.response.ReverseGeocodingResponse;
import com.enten.yeogiya.kakao_maps.controller.response.SearchResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class KakaoMapsServiceImpl implements KakaoMapsService {
    private final WebClient kakaoWebClient;

    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;

    public KakaoMapsServiceImpl(@Qualifier("kakaoWebClient") WebClient kakaoWebClient) {
        this.kakaoWebClient = kakaoWebClient;
    }

    @Override
    public Mono<SearchResponse> searchPlaces(SearchRequest query, SearchType searchType) {
        String path = switch (searchType) {
            case KEYWORD -> "/v2/local/search/keyword.json";
            case CATEGORY -> "/v2/local/search/category.json";
        };
        return kakaoWebClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path(path);

                    if (searchType == SearchType.KEYWORD) {
                        if (query.getQuery() == null || query.getQuery().trim().isEmpty()) {
                            throw new IllegalArgumentException("키워드 검색 시 query는 필수입니다.");
                        }
                        uriBuilder.queryParam("query", query.getQuery());
                    }

                    if (searchType == SearchType.CATEGORY) {
                        if (query.getCategoryGroupCode() == null) {
                            throw new IllegalArgumentException("카테고리 검색 시 category_group_code는 필수입니다.");
                        }
                        if (query.getX() == null || query.getY() == null) {
                            throw new IllegalArgumentException("카테고리 검색 시 x, y 좌표는 필수입니다.");
                        }
                        if (query.getRadius() == null) {
                            throw new IllegalArgumentException("카테고리 검색 시 radius는 필수입니다.");
                        }
                        uriBuilder.queryParam("category_group_code", query.getCategoryGroupCode().name());
                    }

                    if (query.getX() != null && query.getY() != null) {
                        uriBuilder.queryParam("x", query.getX())
                                .queryParam("y", query.getY());
                    }
                    if (query.getRadius() != null) {
                        uriBuilder.queryParam("radius", query.getRadius());
                    }
                    if (query.getRect() != null) {
                        uriBuilder.queryParam("rect", query.getRect());
                    }
                    if (query.getPage() != null) {
                        uriBuilder.queryParam("page", query.getPage());
                    }
                    if (query.getSize() != null) {
                        uriBuilder.queryParam("size", query.getSize());
                    }
                    if (query.getSort() != null) {
                        uriBuilder.queryParam("sort", query.getSort());
                    }

                    return uriBuilder.build();
                })
                .header("Authorization", "KakaoAK " + kakaoRestApiKey)
                .retrieve()
                .bodyToMono(SearchResponse.class);
    }

    @Override
    public Mono<ReverseGeocodingResponse> reverseGeocoding(ReverseGeocodingRequest query) {
        return kakaoWebClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/v2/local/geo/coord2address.json");

                    if (query.getX() == null || query.getY() == null) {
                        throw new IllegalArgumentException("x, y 좌표는 필수입니다.");
                    }
                    uriBuilder.queryParam("x", query.getX())
                            .queryParam("y", query.getY());

                    if (query.getInputCoord() != null) {
                        uriBuilder.queryParam("input_coord", query.getInputCoord());
                    }

                    return uriBuilder.build();
                })
                .header("Authorization", "KakaoAK " + kakaoRestApiKey)
                .retrieve()
                .bodyToMono(ReverseGeocodingResponse.class);
    }

    @Override
    public Mono<MeetingCenterPoint> calculateCenterPoint(List<Map<String, Double>> locations) {
        return Mono.fromCallable(() -> {
            if (locations.isEmpty()) {
                throw new IllegalArgumentException("위치 정보가 없습니다.");
            }

            double centerLat = locations.stream()
                    .mapToDouble(location -> location.get("lat"))
                    .average()
                    .orElseThrow(() -> new IllegalArgumentException("위도 계산 실패"));

            double centerLng = locations.stream()
                    .mapToDouble(location -> location.get("lng"))
                    .average()
                    .orElseThrow(() -> new IllegalArgumentException("경도 계산 실패"));

            return MeetingCenterPoint.builder()
                    .lat(centerLat)
                    .lng(centerLng)
                    .participantCount(locations.size())
                    .build();
        });
    }

    @Override
    public Mono<MeetingPlaceResponse> findMeetingPlace(MeetingPlaceRequest request) {
        if (request.getMeetingLocations() == null || request.getMeetingLocations().isEmpty()) {
            return Mono.error(new IllegalArgumentException("참가자 위치 정보가 필요합니다."));
        }

        List<Map<String, Double>> locationMaps = request.getMeetingLocations().stream()
                .map(location -> Map.of(
                        "lat", location.getLat(),
                        "lng", location.getLng()
                ))
                .toList();

        return calculateCenterPoint(locationMaps)
                .flatMap(centerPoint -> {
                    SearchRequest searchRequest = new SearchRequest();
                    searchRequest.setCategoryGroupCode(request.getCategoryGroupCode());
                    searchRequest.setX(centerPoint.getLng().toString());
                    searchRequest.setY(centerPoint.getLat().toString());
                    searchRequest.setRadius(request.getRadius());
                    searchRequest.setSize(request.getSize());
                    searchRequest.setSort("distance");

                    return searchPlaces(searchRequest, SearchType.CATEGORY)
                            .map(searchResponse -> {
                                MeetingPlaceResponse response = new MeetingPlaceResponse();
                                response.setMeetingCenterPoint(centerPoint);
                                response.setRecommendedPlaces(List.of(searchResponse));

                                return response;
                            });
                })
                .onErrorMap(throwable ->
                        new RuntimeException("약속 장소 검색 중 오류 발생: " + throwable.getMessage(), throwable)
                );
    }
}
